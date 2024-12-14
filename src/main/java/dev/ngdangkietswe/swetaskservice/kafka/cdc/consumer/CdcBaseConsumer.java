package dev.ngdangkietswe.swetaskservice.kafka.cdc.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.ngdangkietswe.swejavacommonshared.debezium.CdcEvent;
import dev.ngdangkietswe.swejavacommonshared.debezium.CdcEventConsumer;
import dev.ngdangkietswe.swetaskservice.data.repository.IBaseRepository;
import dev.ngdangkietswe.swetaskservice.kafka.cdc.payload.CdcBasePayload;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Log4j2
public class CdcBaseConsumer<
        P extends CdcBasePayload<E>,
        E extends Serializable,
        R extends IBaseRepository<E>>
        extends CdcEventConsumer {

    protected final Class<P> clazz;
    protected final R repository;

    protected final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    public CdcBaseConsumer(Class<P> clazz, R repository) {
        this.clazz = clazz;
        this.repository = repository;
    }

    @Override
    protected void process(CdcEvent payload, String topic) {
        switch (payload.getOperationType()) {
            case CREATE, UPDATE, READ -> upsert(payload);
            case DELETE -> delete(payload);
            default ->
                    log.debug("Unsupported operation type: {} in the topic {}", payload.getOperationType().name(), topic);
        }
    }

    protected void upsert(CdcEvent payload) {
        E entity = objectMapper.convertValue(payload.getAfter(), clazz).toEntity();
        repository.save(entity);
    }

    protected void delete(CdcEvent payload) {
        P cdcDto = objectMapper.convertValue(payload.getBefore(), clazz);
        repository.deleteById(cdcDto.getId());
    }
}
