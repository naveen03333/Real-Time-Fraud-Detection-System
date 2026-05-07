package com.example.bankease.FraudDetectionService;


import com.example.bankease.TransactionService.TransactionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionProducer {
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    public FraudDetectionProducer(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void FraudDetected(TransactionEvent transactionEvent){
        kafkaTemplate.send("fraud.alerts", transactionEvent);
    }
}
