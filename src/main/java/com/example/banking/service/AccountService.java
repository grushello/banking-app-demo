package com.example.banking.service;

import com.example.banking.dto.request.CreateAccountRequest;
import com.example.banking.dto.response.AccountResponse;
import com.example.banking.dto.response.ApiResponse;
import com.example.banking.model.Account;
import com.example.banking.util.IbanUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountService {

    private final Map<UUID, Account> accounts = new HashMap<>();

    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(CreateAccountRequest request) {

        /// TO do Validation - Name Mandatory
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .iban(IbanUtil.generateIban())
                .ownerName(request.getOwnerName())
                .balance(request.getBalance())
                .build();

        // Temp Storage
        accounts.put(account.getId(), account);

        ///  To do Confirm to return Full bank account details or masked
        AccountResponse response =  AccountResponse.builder()
                .id(account.getId())
                .iban(IbanUtil.maskIban(account.getIban()))
                .ownerName(account.getOwnerName())
                .build();

        ApiResponse<AccountResponse> apiResponse =
                new ApiResponse<>("Account created successfully", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(UUID id) {

        Account account = accounts.get(id);

        if (account == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Account not found", null));
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
