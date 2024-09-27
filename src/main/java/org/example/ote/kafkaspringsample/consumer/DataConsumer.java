package org.example.ote.kafkaspringsample.consumer;

import lombok.RequiredArgsConstructor;
import org.example.ote.kafkaspringsample.dto.Data;
import org.example.ote.kafkaspringsample.service.Processor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataConsumer {

    private final Processor processor;

    @KafkaListener(id = "data-group-id", topics = "topic1")
    public void listen(Data data) {
        processor.process(data);
    }
}
