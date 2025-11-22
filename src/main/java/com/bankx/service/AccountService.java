package com.bankx.service;

import com.bankx.dto.AccountRequest;
import com.bankx.dto.AccountResponse;
import com.bankx.entity.Account;
import com.bankx.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public AccountResponse openAccount(AccountRequest request){
        if(!request.getAccountType().equalsIgnoreCase("SAVINGS") && !request.getAccountType().equalsIgnoreCase("CURRENT")){
            throw new IllegalArgumentException("Invalid account type.");
        }

        //Generate account number
        String accountNumber= UUID.randomUUID().toString().substring(0,8).toUpperCase();

        // create entity
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setType(request.getAccountType().toUpperCase());
        account.setBalance(BigDecimal.ZERO);

        //save
        Account  saved = repo.save(account);

        //Build response
        AccountResponse response = new AccountResponse();
        response.setId(saved.getId());
        response.setAccountNumber(saved.getAccountNumber());
        response.setAccountType(request.getAccountType());
        response.setBalance(saved.getBalance());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }
}
