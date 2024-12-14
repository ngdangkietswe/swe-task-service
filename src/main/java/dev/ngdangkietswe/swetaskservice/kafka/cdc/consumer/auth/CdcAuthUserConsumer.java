package dev.ngdangkietswe.swetaskservice.kafka.cdc.consumer.auth;

import dev.ngdangkietswe.swejavacommonshared.constants.KafkaConstant;
import dev.ngdangkietswe.swetaskservice.data.entity.CdcAuthUserEntity;
import dev.ngdangkietswe.swetaskservice.data.repository.CdcAuthUserRepository;
import dev.ngdangkietswe.swetaskservice.kafka.cdc.consumer.CdcBaseConsumer;
import dev.ngdangkietswe.swetaskservice.kafka.cdc.payload.auth.CdcAuthUserPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Component
public class CdcAuthUserConsumer extends CdcBaseConsumer<CdcAuthUserPayload, CdcAuthUserEntity, CdcAuthUserRepository> {

    public CdcAuthUserConsumer(CdcAuthUserRepository repository) {
        super(CdcAuthUserPayload.class, repository);
    }

    @KafkaListener(
            topics = KafkaConstant.CDC_AUTH_USER_TOPIC,
            containerFactory = KafkaConstant.STRING_LISTENER_CONTAINER_FACTORY)
    public void consume(ConsumerRecord<String, String> record) {
        accept(record);
    }
}
