package com.example.banking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking.dto.request.TransferRequest;
import com.example.banking.dto.response.TransferResponse;
import com.example.banking.service.TransferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
  private final TransferService transferService;

  @PostMapping
  public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
    return ResponseEntity.ok(transferService.transfer(request));
  }
}
