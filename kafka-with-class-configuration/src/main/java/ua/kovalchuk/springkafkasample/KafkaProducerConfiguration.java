package ua.kovalchuk.springkafkasample;

import java.util.Collections;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration(proxyBeanMethods = false)
public class KafkaProducerConfiguration {

    @Value("${test.spring.kafka.producer.topic-name}")
    private String topicName;

    @Bean
    public ReadinessProducer producer(KafkaTemplate<Object, Object> kafkaTemplate) {
        return new ReadinessProducer(topicName, kafkaTemplate);
    }

    @Bean
    public ProducerFactory<Object, Object> producerFactory(KafkaProperties properties) {
        return new DefaultKafkaProducerFactory<>(overrideProducerConfigs(properties.buildProducerProperties()));
    }

    private Map<String, Object> overrideProducerConfigs(Map<String, Object> properties) {
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return properties;
    }
}
