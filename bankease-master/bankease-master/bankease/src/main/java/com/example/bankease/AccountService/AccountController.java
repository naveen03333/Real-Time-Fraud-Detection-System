package com.example.bankease.AccountService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {
    private  final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    @PostMapping
    public void addNewAccount(@RequestBody Account account){
        accountService.addNewAccount(account);
    }

    @GetMapping
    public List<Account> getNewAccount(){
        return accountService.getAccounts();
    }

    @GetMapping(path = "{accountNumber}")
    public Account getAccount(@PathVariable("accountNumber") String accountNumber){
        return accountService.getAccount(accountNumber);
    }

//    @PutMapping(path = "{accountNumber},{amount}")
//    public void updateBalanceStatus(@PathVariable("accountNumber") String accountNumber,
//                                    @PathVariable("amount") BigDecimal amount){
//        accountService.updateBalanceStatus(accountNumber, amount);
//    }
}
