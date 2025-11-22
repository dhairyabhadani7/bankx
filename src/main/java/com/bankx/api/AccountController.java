package com.bankx.api;

import com.bankx.dto.AccountRequest;
import com.bankx.dto.AccountResponse;
import com.bankx.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public AccountResponse open(@RequestBody AccountRequest request){
        return service.openAccount(request);
    }
}
