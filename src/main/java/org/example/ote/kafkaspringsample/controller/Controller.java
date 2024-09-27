package org.example.ote.kafkaspringsample.controller;

import lombok.RequiredArgsConstructor;
import org.example.ote.kafkaspringsample.producer.DataProducer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final DataProducer producer;

    @PostMapping(path = "/send/{key}/{value}")
    public void sendFoo(@PathVariable("key") String key, @PathVariable("value") String value) {
        producer.send(key, value);
    }
}