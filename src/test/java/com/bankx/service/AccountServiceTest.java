package com.bankx.service;

import com.bankx.dto.AccountRequest;
import com.bankx.entity.Account;
import com.bankx.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountRepository repo;
    private AccountService service;

    @BeforeEach
    void setup(){
        repo= Mockito.mock(AccountRepository.class);
        service=new AccountService(repo);
    }

    @Test
    void createAccount_success(){
        AccountRequest req=new AccountRequest();
        req.setAccountType("SAVINGS");
        req.setInitialBalance(new BigDecimal("100.00"));

        when(repo.save(any(Account.class))).thenAnswer(invocation->{
            Account a = invocation.getArgument(0);
            a.setId(1L);
            a.setCreatedAt(Instant.now());
            return a;
        });
        var resp = service.createAccount(req);
        assertNotNull(resp);
        assertEquals("SAVINGS", resp.getAccountType());
        assertEquals(new BigDecimal("100.00"),resp.getBalance());
        assertNotNull(resp.getAccountNumber());
        assertEquals(1L,resp.getId());
        verify(repo, times(1)).save(any(Account.class));

    }


    @Test
    void getAccountById_notFound(){
        when(repo.findById(100L)).thenReturn(Optional.empty());
        var ex =assertThrows(IllegalArgumentException.class,()-> service.getAccountById(100L));
        assertTrue(ex.getMessage().contains("Account not found"));
    }
}
