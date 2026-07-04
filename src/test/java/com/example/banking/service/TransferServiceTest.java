package com.example.banking.service;

import com.example.banking.dto.request.TransferRequestDTO;
import com.example.banking.dto.response.TransferResponseDTO;
import com.example.banking.exception.InsufficientFundsException;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Account;
import com.example.banking.model.Transaction;
import com.example.banking.model.TransactionType;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
        transferService = new TransferService(accountRepository, transactionRepository);
    }

    @Test
    void shouldTransferMoneyBetweenAccountsAndCreateTransactions() {
        Account source = new Account(UUID.randomUUID(), "LV111", "Source", new BigDecimal("100.00"));
        Account destination = new Account(UUID.randomUUID(), "LV222", "Destination", new BigDecimal("25.00"));
        accountRepository.save(source);
        accountRepository.save(destination);

        TransferRequestDTO request = new TransferRequestDTO(
                source.getId(),
                destination.getId(),
                new BigDecimal("40.00"),
                "Rent"
        );

        TransferResponseDTO response = transferService.transfer(request);

        assertEquals(new BigDecimal("60.00"), source.getBalance());
        assertEquals(new BigDecimal("65.00"), destination.getBalance());
        assertEquals("Transfer completed successfully", response.message());
        assertNotNull(response.timestamp());
        assertTrue(response.transactionId().contains("-"));

        List<Transaction> sourceTransactions = transactionRepository.findByAccountId(source.getId());
        List<Transaction> destinationTransactions = transactionRepository.findByAccountId(destination.getId());

        assertEquals(1, sourceTransactions.size());
        assertEquals(1, destinationTransactions.size());
        assertEquals(TransactionType.TRANSFER_OUT, sourceTransactions.getFirst().getType());
        assertEquals(TransactionType.TRANSFER_IN, destinationTransactions.getFirst().getType());
        assertEquals(new BigDecimal("40.00"), sourceTransactions.getFirst().getAmount());
        assertEquals(new BigDecimal("40.00"), destinationTransactions.getFirst().getAmount());
        assertEquals("Rent", sourceTransactions.getFirst().getNote());
        assertEquals("Rent", destinationTransactions.getFirst().getNote());
    }

    @Test
    void shouldThrowWhenSourceAccountDoesNotExist() {
        Account destination = new Account(UUID.randomUUID(), "LV222", "Destination", new BigDecimal("25.00"));
        accountRepository.save(destination);
        TransferRequestDTO request = new TransferRequestDTO(
                UUID.randomUUID(),
                destination.getId(),
                new BigDecimal("10.00"),
                "Missing source"
        );

        assertThrows(ResourceNotFoundException.class, () -> transferService.transfer(request));
    }

    @Test
    void shouldThrowWhenDestinationAccountDoesNotExist() {
        Account source = new Account(UUID.randomUUID(), "LV111", "Source", new BigDecimal("100.00"));
        accountRepository.save(source);
        TransferRequestDTO request = new TransferRequestDTO(
                source.getId(),
                UUID.randomUUID(),
                new BigDecimal("10.00"),
                "Missing destination"
        );

        assertThrows(ResourceNotFoundException.class, () -> transferService.transfer(request));
    }

    @Test
    void shouldThrowWhenSourceHasInsufficientFunds() {
        Account source = new Account(UUID.randomUUID(), "LV111", "Source", new BigDecimal("20.00"));
        Account destination = new Account(UUID.randomUUID(), "LV222", "Destination", new BigDecimal("25.00"));
        accountRepository.save(source);
        accountRepository.save(destination);
        TransferRequestDTO request = new TransferRequestDTO(
                source.getId(),
                destination.getId(),
                new BigDecimal("40.00"),
                "Too much"
        );

        assertThrows(InsufficientFundsException.class, () -> transferService.transfer(request));
        assertEquals(new BigDecimal("20.00"), source.getBalance());
        assertEquals(new BigDecimal("25.00"), destination.getBalance());
        assertTrue(transactionRepository.findByAccountId(source.getId()).isEmpty());
        assertTrue(transactionRepository.findByAccountId(destination.getId()).isEmpty());
    }
}
