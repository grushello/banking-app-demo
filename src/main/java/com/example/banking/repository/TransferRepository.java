package com.example.banking.repository;

import com.example.banking.model.Transfer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class TransferRepository {
    private final List<Transfer> transfers = new CopyOnWriteArrayList<>();

    public Transfer save(Transfer transfer) {
        transfers.add(transfer);
        return transfer;
    }

    public List<Transfer> findByUserId(UUID id) {
        return transfers.stream()
                .filter(t -> t.getAccount().getId().equals(id))
                .collect(Collectors.toList());
    }
}
