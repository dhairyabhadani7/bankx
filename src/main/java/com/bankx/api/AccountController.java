package com.bankx.api;

import com.bankx.dto.AccountRequest;
import com.bankx.dto.AccountResponse;
import com.bankx.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request){
        AccountResponse response= service.createAccount(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("id") Long id){
        AccountResponse response= service.getAccountById(id);
        return  ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> listAccounts(){
        List<AccountResponse> list = service.listAccounts();
        return ResponseEntity.ok(list);
    }
}
