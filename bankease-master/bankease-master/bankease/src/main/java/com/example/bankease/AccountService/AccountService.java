package com.example.bankease.AccountService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountEventProducer accountEventProducer;
    private final ObjectMapper objectMapper;

    public AccountService(AccountRepository accountRepository, AccountEventProducer accountEventProducer, ObjectMapper objectMapper){
        this.accountRepository = accountRepository;
        this.accountEventProducer = accountEventProducer;
        this.objectMapper = objectMapper;
    }

    public void addNewAccount(Account account){
        Optional<Account> accountRepositoryByAccountNumberAndEmailAndPhoneNumber = accountRepository.findByAccountNumberAndEmailAndPhoneNumber(account.getAccountNumber(), account.getEmail(), account.getPhoneNumber());

        if(accountRepositoryByAccountNumberAndEmailAndPhoneNumber.isPresent()){
            throw new IllegalStateException("Account already exists");
        }
        accountRepository.save(account);
        try{
            accountEventProducer.publishAccountCreated(account);
        }
        catch (Exception e){
            throw new IllegalStateException("Error");
        }
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(String accountNumber){
        return accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(()->
                new IllegalStateException("No Account found"));
    }

    public void updateBalanceStatus(String accountNumber, BigDecimal amount) {
        System.out.println(accountNumber);
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new IllegalStateException("Account does not exists"));
        if(amount != null){
            BigDecimal currAmount = account.getBalance().add(amount);
            account.setBalance(currAmount);
            accountRepository.save(account);
        }
    }
}
