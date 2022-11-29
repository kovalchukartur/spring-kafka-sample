package ua.kovalchuk.springkafkasample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@Configuration
public class KafkaProducer {

    private final KafkaProperties kafkaProperties;

    public KafkaProducer(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, Object> template) {
        ReadinessMessage readinessMessage = ReadinessMessage.builder()
            .readinessId("SpringKafkaSampleApplication1234")
            .build();

        return args -> {
            String topic = kafkaProperties.getTopic();
            template.send(topic, readinessMessage)
                .addCallback(
                    result -> log.info("Send message = {} to topic = {}", readinessMessage, topic),
                    ex -> log.error(String.format("The message = %s to topic = %s was not send", readinessMessage, topic), ex)
                );
        };
    }
}
