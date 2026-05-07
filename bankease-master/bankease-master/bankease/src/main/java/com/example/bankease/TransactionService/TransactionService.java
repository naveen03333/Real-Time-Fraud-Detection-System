package com.example.bankease.TransactionService;

import com.example.bankease.AccountService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionEventProducer transactionEventProducer;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountService accountService,
                              TransactionEventProducer transactionEventProducer){
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionEventProducer = transactionEventProducer;
    }

    public void addMoney(Transaction transaction) {
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("PENDING");
        transactionRepository.save(transaction);

        accountService.updateBalanceStatus(transaction.getAccountNumber(), transaction.getAmount());
        transaction.setStatus("SUCCESS");
        transactionRepository.save(transaction);

        TransactionEvent transactionEvent = new TransactionEvent(
                transaction.getTransactionId(),
                transaction.getAccountNumber(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getTransactionDate()
        );
        transactionEventProducer.publishTransactionCompleted(transactionEvent);
    }
}
