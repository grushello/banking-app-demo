package com.example.banking.controller;

import com.example.banking.dto.request.TransferRequest;
import com.example.banking.dto.response.TransferResponse;
import com.example.banking.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public List<TransferResponse> transfer(
            @Valid @RequestBody TransferRequest request) {

        return transferService.transfer(request);
    }
}