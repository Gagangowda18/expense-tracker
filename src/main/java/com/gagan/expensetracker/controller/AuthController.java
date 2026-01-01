package com.gagan.expensetracker.controller;

import com.gagan.expensetracker.dto.AuthResponse;
import com.gagan.expensetracker.dto.LoginRequest;
import com.gagan.expensetracker.dto.SignupRequest;
import com.gagan.expensetracker.service.UserService;
import com.gagan.expensetracker.security.SecurityUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final UserService userService;

    // 🔹 SIGNUP + return JWT
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest req) {
        AuthResponse response = userService.registerAndAuthenticate(req);
        return ResponseEntity.ok(response);
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        AuthResponse response = userService.authenticate(req);
        return ResponseEntity.ok(response);
    }

    private Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
}
