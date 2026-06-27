package com.auth.authproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth.authproject.dto.AuthRequest;
import com.auth.authproject.dto.RegisterRequest;
import com.auth.authproject.dto.AuthResponse;
import com.auth.authproject.dto.RefreshRequest;
import com.auth.authproject.dto.LogoutRequest;
import com.auth.authproject.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login.html";
    }
    
    // 🔹 REGISTER
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201).body(authService.register(request)); // ✅ improved
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // 🔹 REFRESH TOKEN
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) { // ✅ FIXED
        return ResponseEntity.ok(
                authService.refreshToken(request.getRefreshToken())
        );
    }

    // 🔹 LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody LogoutRequest request) {
        return ResponseEntity.ok(
                authService.logout(request.getRefreshToken())
        );
    }
}