package dev.ngdangkietswe.swetaskservice.data.utils;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import dev.ngdangkietswe.swejavacommonshared.domain.Page;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Pageable;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcUtil;

import java.util.List;
import java.util.Map;

/**
 * @author ngdangkietswe
 * @since 7/15/2024
 */

public class PageUtils {

    private PageUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T, E> Page<T> paginate(Pageable pageable,
                                       JPAQuery<T> baseQuery,
                                       EntityPathBase<E> qEntity,
                                       Map<String, ComparableExpressionBase<?>> orderMap,
                                       ComparableExpressionBase<?> defaultOrder,
                                       Expression<?>... expressions) {
        JPAQuery<?> selectQuery = baseQuery.clone()
                .orderBy(getOrderSpecifier(pageable, orderMap, defaultOrder));

        if (!pageable.getUnPaged()) {
            applyPagination(selectQuery, pageable);
        }

        List<T> records = expressions.length > 1
                ? (List<T>) selectQuery.select(expressions).fetch()
                : (List<T>) selectQuery.select(expressions[0]).fetch();

        long totalItems = pageable.getUnPaged()
                ? records.size()
                : baseQuery.select(qEntity.count()).fetchFirst();

        return Page.<T>builder()
                .items(records)
                .totalItems(totalItems)
                .build();
    }

    public static void applyPagination(JPAQuery<?> selectQuery, Pageable pageable) {
        selectQuery.limit(pageable.getSize())
                .offset(GrpcUtil.calculateOffset(pageable.getPage(), pageable.getSize()));
    }

    public static OrderSpecifier<?> getOrderSpecifier(Pageable pageable,
                                                      Map<String, ComparableExpressionBase<?>> orderMap,
                                                      ComparableExpressionBase<?> defaultOrder) {
        Order direction = Order.valueOf(pageable.getDirection());
        String sortField = pageable.getSort();
        ComparableExpressionBase<?> sortExpression = orderMap.getOrDefault(sortField, defaultOrder);

        return new OrderSpecifier<>(direction, sortExpression, OrderSpecifier.NullHandling.NullsLast);
    }
}
