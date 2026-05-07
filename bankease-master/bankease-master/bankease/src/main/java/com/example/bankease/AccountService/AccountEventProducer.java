package com.example.bankease.AccountService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer {
    private final KafkaTemplate<String, Account> kafkaTemplate;

    public AccountEventProducer(
            @Qualifier("accountEventKafkaTemplate") KafkaTemplate<String, Account> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishAccountCreated(Account event) {
        kafkaTemplate.send("account.created", event);
    }
}

