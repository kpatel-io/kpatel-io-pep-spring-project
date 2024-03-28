package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // Method to persist an account
    public Account persistAccount(Account account){
        return accountRepository.save(account);
    }

    // Method to register a new account
    public Account registerAccount(Account account) {
        // Save the account to the database
        return accountRepository.save(account);
    }

    // Method to login an existing account
    public Account loginAccount(Account account) {
        // Check if an account with the given username and password exists in the database
        Optional<Account> loginAccount = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(loginAccount.isPresent()) {
            // Return the account if found
            return loginAccount.get();
        } else {
            // Return null if not found
            return null;
        }
    }

    // Method to check if there is a matching account username
    public boolean isThereMatchingAccountUsername(String username) {
        // Check if an account with the given username exists in the database
        Optional<Account> optAcc = accountRepository.findAccountByUsername(username);
        return optAcc.isPresent();
    }
}