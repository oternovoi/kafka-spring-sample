package org.example.ote.kafkaspringsample.service;

import org.example.ote.kafkaspringsample.dto.Data;

public interface Processor {
    void process(Data data);
}
