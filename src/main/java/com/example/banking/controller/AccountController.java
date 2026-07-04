package com.example.banking.controller;

import com.example.banking.dto.request.CreateAccountRequest;
import com.example.banking.dto.response.AccountResponse;
import com.example.banking.dto.response.ApiResponse;
import com.example.banking.dto.response.TransactionResponse;
import com.example.banking.service.AccountService;
import com.example.banking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable UUID id) {

        return accountService.getAccount(id);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @PathVariable String id) {

        return ResponseEntity.ok(transactionService.getAccountStatement(id));
    }

}