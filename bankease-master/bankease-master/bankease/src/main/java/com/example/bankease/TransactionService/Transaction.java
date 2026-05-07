package com.example.bankease.TransactionService;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
public class Transaction {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private UUID transactionId;
    private String accountNumber;
    private String transactionType;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime transactionDate;
    private String location;
    private String ipAddress;
    private String deviceId;
    private String status;

    public Transaction(
                       String accountNumber,
                       String transactionType,
                       BigDecimal amount,
                       String currency,
                       LocalDateTime transactionDate,
                       String location,
                       String ipAddress,
                       String deviceId
                       ){
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.location = location;
        this.ipAddress = ipAddress;
        this.deviceId = deviceId;
    }

    public Transaction(){}

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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

