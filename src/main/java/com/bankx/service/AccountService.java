package com.bankx.service;

import com.bankx.dto.AccountRequest;
import com.bankx.dto.AccountResponse;
import com.bankx.entity.Account;
import com.bankx.repository.AccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        String type = request.getAccountType().toUpperCase().trim();
        if (!type.equals("SAVINGS")
                && !type.equals("CURRENT")) {
            throw new IllegalArgumentException("Invalid account type. Must be SAVINGS or CURRENT");
        }

        // create entity
        Account acc = new Account();
        acc.setAccountNumber(generateAccountNumber());
        acc.setType(type);
        BigDecimal init = request.getInitialBalance()==null? BigDecimal.ZERO:request.getInitialBalance();
        acc.setBalance(init);
        acc.setCreatedAt(java.time.Instant.now());

        //save
        Account saved = repo.save(acc);
        return toResponse(saved);
    }

    public AccountResponse getAccountById(Long id) {
        Optional<Account> opt= repo.findById(id);
        return opt.map(this::toResponse).orElseThrow(() -> new IllegalArgumentException("Account not found with id: "+ id));
    }

    public List<AccountResponse> listAccounts() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private AccountResponse toResponse(Account a){
        AccountResponse response = new AccountResponse();
        response.setId(a.getId());
        response.setAccountNumber(a.getAccountNumber());
        response.setAccountType(a.getType());
        response.setBalance(a.getBalance());
        response.setCreatedAt(a.getCreatedAt());

        return response;
    }


        private String generateAccountNumber(){
        String prefix = "BX";
        long rand = System.currentTimeMillis() % 1_000_000_000L;
        return prefix+String.format("%09d",rand);

    }
}
