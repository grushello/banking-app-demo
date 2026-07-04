package com.example.banking.service;

import com.example.banking.dto.request.CreateAccountRequest;
import com.example.banking.dto.response.AccountResponse;
import com.example.banking.dto.response.ApiResponse;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Account;
import com.example.banking.model.User;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.UserRepository;
import com.example.banking.util.IbanUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(CreateAccountRequest request) {

        User owner = userRepository.findByUsername(request.getOwnerName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Account account = Account.builder()
                .id(UUID.randomUUID())
                .iban(IbanUtil.generateIban())
                .ownerName(owner.getUsername())
                .balance(request.getBalance())
                .build();

        // Repo
        accountRepository.save(account);

        AccountResponse response =  AccountResponse.builder()
                .id(account.getId())
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
            throw new ResourceNotFoundException("Account not found");
        }

        AccountResponse response = AccountResponse.builder()
                .id(account.getId())
                .iban(account.getIban())
                .ownerName(account.getOwnerName())
                .balance(account.getBalance())
                .build();

        return ResponseEntity.ok(new ApiResponse<>("Success", response));
    }


}
