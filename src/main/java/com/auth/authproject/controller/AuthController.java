package com.auth.authproject.controller;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import com.auth.authproject.dto.AuthRequest;
import com.auth.authproject.dto.RegisterRequest;
import com.auth.authproject.dto.AuthResponse;
import com.auth.authproject.dto.ForgotPasswordRequest;
import com.auth.authproject.dto.ForgotPasswordResponse;
import com.auth.authproject.dto.RefreshRequest;
import com.auth.authproject.dto.ResetPasswordRequest;
import com.auth.authproject.dto.LogoutRequest;
import com.auth.authproject.service.AuthService;
import com.auth.authproject.service.PasswordResetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;
    private final Environment environment;

    public AuthController(AuthService authService, PasswordResetService passwordResetService, Environment environment) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
        this.environment = environment;
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

    @GetMapping("/admin-login-defaults")
    public ResponseEntity<Map<String, String>> adminLoginDefaults() {
        if (!environment.matchesProfiles("local")) {
            return ResponseEntity.noContent().build();
        }

        String adminEmail = environment.getProperty("app.admin.email", "");
        String adminPassword = environment.getProperty("app.admin.password", "");

        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(Map.of(
                "email", adminEmail,
                "password", adminPassword
        ));
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

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(passwordResetService.createResetToken(request.getEmail()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(
                passwordResetService.resetPassword(request.getToken(), request.getPassword())
        );
    }
}
