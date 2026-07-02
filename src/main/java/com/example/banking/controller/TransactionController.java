package com.example.banking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking.dto.request.TransactionRequest;
import com.example.banking.dto.response.TransactionResponse;
import com.example.banking.model.Transaction;
import com.example.banking.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping
  public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request){
    Transaction transaction = transactionService.transfer(request);
    return ResponseEntity.ok(TransactionResponse.from(transaction));
  }
}
