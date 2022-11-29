package ua.kovalchuk.springkafkasample;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class ReadinessProducer {

    private final String topic;
    private final KafkaTemplate<Object, Object> template;

    @PostConstruct
    public void saveData() {
        ReadinessMessage readinessMessage = ReadinessMessage.builder()
            .readinessId("test")
            .build();

        template.send(topic, readinessMessage)
            .addCallback(
                result -> log.info("The message was sent successfully"),
                ex -> log.error("The message was not send", ex)
            );
    }
}
