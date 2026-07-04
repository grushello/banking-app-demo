package com.example.banking.service;

import com.example.banking.dto.request.CreateUserRequest;
import com.example.banking.dto.response.ApiResponse;
import com.example.banking.dto.response.UserResponse;
import com.example.banking.exception.DuplicateResourceException;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.model.Role;
import com.example.banking.model.User;
import com.example.banking.repository.UserRepository;
import com.example.banking.util.PasswordUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse<UserResponse>> createUser(CreateUserRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new DuplicateResourceException("Username already exists");
                });

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(request.getUsername())
                .passwordHash(PasswordUtil.hashPassword(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User created successfully", response));
    }

    public ResponseEntity<ApiResponse<UserResponse>> getUser(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();

        return ResponseEntity.ok(new ApiResponse<>("Success", response));
    }
}