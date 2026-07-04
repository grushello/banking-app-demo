package com.example.banking.service;

import com.example.banking.dto.response.TransferResponse;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Account;
import com.example.banking.model.Transfer;
import com.example.banking.model.TransferType;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    private AccountRepository accountRepository;
    private TransferRepository transferRepository;
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        transferRepository = new TransferRepository();
        transferService = new TransferService(transferRepository, accountRepository);
    }

    @Test
    void shouldReturnTransfersSortedByNewestFirst() {

        // Arrange
        UUID accountId = UUID.randomUUID();

        Account account = new Account(
                accountId,
                "TR12345",
                "John Doe",
                BigDecimal.ZERO
        );

        accountRepository.save(account);

        Transfer olderTransfer = new Transfer(
                UUID.randomUUID(),
                account,
                TransferType.DEPOSIT,
                new BigDecimal("100"),
                LocalDateTime.now().minusDays(1),
                "Older deposit"
        );

        Transfer newerTransfer = new Transfer(
                UUID.randomUUID(),
                account,
                TransferType.WITHDRAWAL,
                new BigDecimal("50"),
                LocalDateTime.now(),
                "New withdrawal"
        );

        transferRepository.save(olderTransfer);
        transferRepository.save(newerTransfer);

        // Act
        List<TransferResponse> result =
                transferService.getAccountStatement(accountId.toString());

        // Assert
        assertEquals(2, result.size());
        assertEquals("New withdrawal", result.get(0).note());
        assertEquals("Older deposit", result.get(1).note());
    }

    @Test
    void shouldReturnEmptyListWhenAccountHasNoTransfers() {

        // Arrange
        UUID accountId = UUID.randomUUID();

        Account account = new Account(
                accountId,
                "TR12345",
                "John Doe",
                BigDecimal.ZERO
        );

        accountRepository.save(account);

        // Act
        List<TransferResponse> result =
                transferService.getAccountStatement(accountId.toString());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {

        // Arrange
        UUID accountId = UUID.randomUUID();

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> transferService.getAccountStatement(accountId.toString())
        );
    }

    @Test
    void shouldMapTransferToTransferResponse() {

        // Arrange
        UUID accountId = UUID.randomUUID();

        Account account = new Account(
                accountId,
                "TR12345",
                "John Doe",
                BigDecimal.ZERO
        );

        accountRepository.save(account);

        Transfer transfer = new Transfer(
                UUID.randomUUID(),
                account,
                TransferType.DEPOSIT,
                new BigDecimal("250"),
                LocalDateTime.now(),
                "Salary"
        );

        transferRepository.save(transfer);

        // Act
        List<TransferResponse> result =
                transferService.getAccountStatement(accountId.toString());

        // Assert
        TransferResponse response = result.getFirst();

        assertEquals(transfer.getId().toString(), response.id());
        assertEquals(transfer.getType().name(), response.type());
        assertEquals(transfer.getAmount(), response.amount());
        assertEquals(transfer.getNote(), response.note());
    }
}