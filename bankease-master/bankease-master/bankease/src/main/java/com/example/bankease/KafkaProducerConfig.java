package com.example.bankease;

import com.example.bankease.AccountService.Account;
import com.example.bankease.TransactionService.TransactionEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private Map<String, Object> baseProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return props;
    }

    @Bean
    public ProducerFactory<String, TransactionEvent> transactionEventProducerFactory() {
        Map<String, Object> props = baseProducerProps();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, TransactionEvent> transactionEventKafkaTemplate() {
        return new KafkaTemplate<>(transactionEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Account> accountEventProducerFactory() {
        Map<String, Object> props = baseProducerProps();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Account> accountEventKafkaTemplate() {
        return new KafkaTemplate<>(accountEventProducerFactory());
    }
}
