package com.example.banking.controller;

import com.example.banking.dto.request.CreateAccountRequest;
import com.example.banking.dto.response.AccountResponse;
import com.example.banking.dto.response.ApiResponse;
import com.example.banking.dto.response.TransferResponse;
import com.example.banking.service.AccountService;
import com.example.banking.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final TransferService transferService;

    public AccountController(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable UUID id) {

        return accountService.getAccount(id);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransferResponse>> getTransactions(
            @PathVariable UUID id) {

        return ResponseEntity.ok(transferService.getAccountStatement(id));
    }

}