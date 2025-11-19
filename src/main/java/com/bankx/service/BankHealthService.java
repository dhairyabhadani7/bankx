package com.bankx.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class BankHealthService {
    public Map<String, Object> getHealthStatus(){
        return Map.of("service","BankX Backend",
                "status", "UP",
                "timestamp", Instant.now().toString());

    }
}
