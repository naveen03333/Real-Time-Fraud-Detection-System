package com.example.bankease.TransactionService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventProducer{
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public TransactionEventProducer(
            @Qualifier("transactionEventKafkaTemplate") KafkaTemplate<String, TransactionEvent> kafkaTemplate
    ){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTransactionCompleted(TransactionEvent event) {
        kafkaTemplate.send("transaction.completed", event);
    }
}

