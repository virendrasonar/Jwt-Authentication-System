package com.auth.authproject.service;

import com.auth.authproject.dto.AuthRequest;
import com.auth.authproject.dto.RegisterRequest;
import com.auth.authproject.dto.AuthResponse;
import com.auth.authproject.entity.User;
import com.auth.authproject.entity.RefreshToken;
import com.auth.authproject.repository.UserRepository;
import com.auth.authproject.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    // 🔹 REGISTER
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        // 🔐 access token
        String accessToken = jwtService.generateToken(savedUser.getEmail());

        // 🔄 clean old tokens
        refreshTokenService.deleteByUserId(savedUser.getId());

        // 🔄 create new refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    // 🔹 LOGIN
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtService.generateToken(user.getEmail());

        // 🔄 clean old tokens
        refreshTokenService.deleteByUserId(user.getId());

        // 🔄 create new refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    // 🔹 REFRESH TOKEN
    public AuthResponse refreshToken(String requestToken) {

        RefreshToken refreshToken = refreshTokenService.findByToken(requestToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshTokenService.verifyExpiration(refreshToken);

        String accessToken = jwtService.generateToken(
                refreshToken.getUser().getEmail()
        );

        return new AuthResponse(accessToken, requestToken);
    }
 // 🔹 LOGOUT
    public String logout(String requestToken) {

        RefreshToken token = refreshTokenService.findByToken(requestToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // delete token
        refreshTokenService.deleteByUserId(token.getUser().getId());

        return "Logged out successfully";
    }
}