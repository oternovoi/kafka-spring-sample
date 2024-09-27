package org.example.ote.kafkaspringsample.producer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.ote.kafkaspringsample.dto.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@Import(value = {KafkaAutoConfiguration.class, DataProducer.class})
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EmbeddedKafka(kraft = true, partitions = 1)
public class DataProducerTest {

    @Autowired
    private DataProducer producer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void produce() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-id");

        StringDeserializer keyDeserializer = new StringDeserializer();
        JsonDeserializer<Data> valueDeserializer = new JsonDeserializer<>();
        valueDeserializer.addTrustedPackages(Data.class.getPackageName());
        try (Consumer<String, Data> consumer = new KafkaConsumer<>(props, keyDeserializer, valueDeserializer)) {
            consumer.subscribe(List.of("topic1"));
            int attemptCount = 0;
            while (consumer.assignment().size() == 0) {
                consumer.poll(Duration.ofMillis(100));
                if (attemptCount++ > 30) {
                    fail();
                }
            }
            assertEquals(1, consumer.assignment().size());

            producer.send("test", "value");

            ConsumerRecords<String, Data> records = consumer.poll(Duration.ofMillis(1000));
            assertEquals(1, records.count());
            ConsumerRecord<String, Data> record = records.records("topic1").iterator().next();
            assertEquals("test", record.key());
            assertEquals("value", record.value().getValue());
        }
    }
}