package com.example.bankease.AccountService;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table

public class Account {
    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "account_sequence"
    )
    private Long id;
    private String accountNumber;
    private String CustomerName;
    private String email;
    private String phoneNumber;
    private BigDecimal balance;
    private String accountType;
    private LocalDateTime creditedAt;
    private String status;

    public Account(
                   String accountNumber,
                   String customerName,
                   String email,
                   String phoneNumber,
                   BigDecimal balance,
                   String accountType,
                   LocalDateTime creditedAt,
                   String status){
        this.accountNumber = accountNumber;
        this.CustomerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.creditedAt = creditedAt;
        this.status = status;
    }

    public Account(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getCreditedAt() {
        return creditedAt;
    }

    public void setCreditedAt(LocalDateTime creditedAt) {
        this.creditedAt = creditedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", customerName='" + CustomerName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", creditedAt=" + creditedAt +
                ", status='" + status + '\'' +
                '}';
    }

}
