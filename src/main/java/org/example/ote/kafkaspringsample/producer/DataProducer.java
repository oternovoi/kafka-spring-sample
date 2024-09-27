package org.example.ote.kafkaspringsample.producer;

import lombok.RequiredArgsConstructor;
import org.example.ote.kafkaspringsample.dto.Data;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataProducer {

    private final KafkaTemplate<String, Data> template;

    public void send(String key, String value) {
        template.send("topic1", key, new Data(value));
    }
}
