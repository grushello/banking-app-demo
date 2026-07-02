package com.example.banking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {
    private final UUID id;
    private String username;

    @Setter String passwordHash;
    @Setter private Role role;
}
