package com.example.bankease.AccountService;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository <Account, Long> {
    Optional<Account> findByAccountNumberAndEmailAndPhoneNumber(
            String accountNumber, String email, String phoneNumber);
    Optional<Account> findAccountByAccountNumber(String accountNumber);


}
