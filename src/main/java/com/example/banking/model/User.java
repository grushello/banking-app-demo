package com.example.banking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class User {

    private final UUID id;
    private final String username;

    @Setter
    private String passwordHash;

    @Setter
    private Role role;
}