package org.example.ote.kafkaspringsample.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.ote.kafkaspringsample.dto.Data;
import org.example.ote.kafkaspringsample.service.Processor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProcessorImpl implements Processor {

    @Override
    public void process(Data data) {
        log.info("process {}", data);
    }
}
