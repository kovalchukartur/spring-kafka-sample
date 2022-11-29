package ua.kovalchuk.springkafkasample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.mockito.Mockito.when;

@MockitoSettings
class ReadinessProducerTest {

    @Mock
    private KafkaTemplate<Object, Object> kafkaTemplate;

    private ReadinessProducer producer;

    @BeforeEach
    void setup() {
        producer = new ReadinessProducer("topic", kafkaTemplate);
    }

    @Test
    void sendMessage() {
        final ReadinessMessage message = ReadinessMessage.builder().readinessId("test").build();
        when(kafkaTemplate.send("topic", message)).thenReturn(new SettableListenableFuture<>());

        producer.saveData();
    }
}
