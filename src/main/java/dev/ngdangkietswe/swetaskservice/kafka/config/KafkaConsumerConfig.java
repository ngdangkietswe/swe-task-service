package dev.ngdangkietswe.swetaskservice.kafka.config;

import dev.ngdangkietswe.swejavacommonshared.constants.KafkaConstant;
import dev.ngdangkietswe.swejavacommonshared.kafka.config.BaseKafkaConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Configuration
public class KafkaConsumerConfig extends BaseKafkaConsumerConfig {

    @Bean(name = KafkaConstant.JSON_LISTENER_CONTAINER_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> jsonKafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        return this.getJsonKafkaListenerContainerFactory(kafkaProperties);
    }

    @Bean(name = KafkaConstant.JSON_KAFKA_CONSUMER_FACTORY)
    public ConsumerFactory<String, Object> jsonConsumerFactory(KafkaProperties kafkaProperties) {
        return this.getJsonConsumerFactory(kafkaProperties);
    }

    @Bean(name = KafkaConstant.STRING_LISTENER_CONTAINER_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> stringKafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        return this.getStringKafkaListenerContainerFactory(kafkaProperties);
    }

    @Bean(name = KafkaConstant.STRING_KAFKA_CONSUMER_FACTORY)
    public ConsumerFactory<String, Object> stringConsumerFactory(KafkaProperties kafkaProperties) {
        return this.getStringConsumerFactory(kafkaProperties);
    }
}
