package com.example.bankease.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionEvent {
    private UUID transactionId;
    private String accountNumber;
    private BigDecimal amount;
    private String status;
    private LocalDateTime transactionDate;

    public TransactionEvent(
            UUID transactionId,
            String accountNumber,
            BigDecimal amount,
            String status,
            LocalDateTime transactionDate
    ){
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.status = status;
        this.transactionDate = transactionDate;
    }
    public TransactionEvent(){}

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "transactionId='" + transactionId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                '}';
    }

}
