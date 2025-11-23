package com.bankx.api;

import com.bankx.entity.Account;
import com.bankx.repository.AccountRepository;
import com.bankx.service.BankHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@RestController
public class HealthController {


    private final BankHealthService healthService;
    @Autowired
    private AccountRepository repo;

    public HealthController(BankHealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/api/test-account")
    public Object testAccount(){
        Account acc= new Account();
        acc.setAccountNumber("ACC001");
        acc.setType("SAVINGS");
        acc.setBalance(new BigDecimal("1000"));
        return repo.save(acc);
    }

    @GetMapping("/api/health")
    public Object health(){
        return healthService.getHealthStatus();
    }
}
