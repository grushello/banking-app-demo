package com.example.banking.service;

import com.example.banking.dto.request.CreateAccountRequest;
import com.example.banking.dto.response.AccountResponse;
import com.example.banking.dto.response.ApiResponse;
import com.example.banking.model.Account;
import com.example.banking.repository.AccountRepository;
import com.example.banking.util.IbanUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(CreateAccountRequest request) {

        Account account = Account.builder()
                .userId(UUID.randomUUID())
                .iban(IbanUtil.generateIban())
                .ownerName(request.getOwnerName())
                .balance(request.getBalance())
                .build();

        // Repo
        accountRepository.save(account);

        AccountResponse response =  AccountResponse.builder()
                .id(account.getUserId())
                .iban(IbanUtil.maskIban(account.getIban()))
                .ownerName(account.getOwnerName())
                .balance(account.getBalance())
                .build();

        ApiResponse<AccountResponse> apiResponse =
                new ApiResponse<>("Account created successfully", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(UUID id) {

        Account account = accountRepository.findById(id).orElse(null);

        if (account == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Account not found", null));
        }

        AccountResponse response = AccountResponse.builder()
                .id(account.getUserId())
                .iban(account.getIban())
                .ownerName(account.getOwnerName())
                .balance(account.getBalance())
                .build();

        return ResponseEntity.ok(new ApiResponse<>("Success", response));
    }


}
