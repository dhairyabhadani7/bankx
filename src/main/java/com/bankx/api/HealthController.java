package com.bankx.api;

import com.bankx.service.BankHealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class HealthController {


    private final BankHealthService healthService;

    public HealthController(BankHealthService healthService) {
        this.healthService = healthService;
    }


    @GetMapping("/api/health")
    public Object health(){
        return healthService.getHealthStatus();
    }
}
