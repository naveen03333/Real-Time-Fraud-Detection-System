package com.example.bankease.FraudDetectionService;

import com.example.bankease.TransactionService.TransactionEvent;
import com.example.bankease.TransactionService.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class FraudDetectionService {
    private final Map<String, Deque<TransactionEvent>> transactionEventHashMap= new HashMap<>();
    private final int MAX_COUNT = 50;
    private final FraudDetectionProducer fraudDetectionProducer;

    public FraudDetectionService(FraudDetectionProducer fraudDetectionProducer ){
        this.fraudDetectionProducer = fraudDetectionProducer ;
    }

    @KafkaListener(
            topics = "transaction.completed",
            groupId = "transaction-service",
            containerFactory = "transactionEventKafkaListenerContainerFactory"
    )
    public void AddTransactionEvent(TransactionEvent transactionEvent){
        String accountNumber = transactionEvent.getAccountNumber();

        Deque<TransactionEvent> history = transactionEventHashMap
                .computeIfAbsent(accountNumber, k -> new ArrayDeque<>());

        if (history.size() >= MAX_COUNT){
            history.removeFirst();
        }
        history.add(transactionEvent);
        CheckFraud(accountNumber, history);
    }

    public void CheckFraud(String accountNumber, Deque<TransactionEvent> transactions){
        if(transactions.size() < 2) return;
        System.out.println(transactions);

        Iterator<TransactionEvent> iterator = transactions.descendingIterator();
        BigDecimal amount = BigDecimal.ZERO;
        TransactionEvent lastTransaction = transactions.getLast();
        BigDecimal lastTransactionAmount = lastTransaction.getAmount();
        iterator.next();
        int count = 0;
        while (iterator.hasNext()){
            amount = amount.add(iterator.next().getAmount());
            count += 1;
        }
        if(checkLastFiveTransaction(transactions) || checkAmountAverage(lastTransactionAmount , amount, count)){
            System.out.println("Suspicious Transaction");
            fraudDetectionProducer.FraudDetected(lastTransaction);
        }
    }

    public boolean checkLastFiveTransaction(Deque<TransactionEvent> history){
        if (history.size() < 5){
            return false;
        }
        List<TransactionEvent> lastFive = history.stream().skip(
                Math.max(0,history.size() - 5))
                .toList();

        TransactionEvent firstOfFive = lastFive.get(0);
        TransactionEvent lastOfFive = lastFive.get(lastFive.size() - 1);
        long diffMills = java.time.Duration.between(
                firstOfFive.getTransactionDate(), lastOfFive.getTransactionDate()).toMillis();
        return diffMills <= 60_000;
    }

    public boolean checkAmountAverage(BigDecimal lastTransactionAmount ,BigDecimal amount, int size){
        if (size == 0) return false;
        BigDecimal diff = amount.subtract(lastTransactionAmount);
        BigDecimal average = amount.divide(BigDecimal.valueOf(size), RoundingMode.HALF_UP);
        return diff.compareTo(average.multiply(BigDecimal.valueOf(3))) > 0;
    }

}

