package com.auth.authproject.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.authproject.dto.ForgotPasswordResponse;
import com.auth.authproject.entity.PasswordResetToken;
import com.auth.authproject.entity.User;
import com.auth.authproject.repository.PasswordResetTokenRepository;
import com.auth.authproject.repository.UserRepository;

@Service
public class PasswordResetService {

    private static final long RESET_TOKEN_DURATION = 15 * 60 * 1000L;

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public PasswordResetService(UserRepository userRepository,
                                PasswordResetTokenRepository passwordResetTokenRepository,
                                PasswordEncoder passwordEncoder,
                                RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public ForgotPasswordResponse createResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No account found for this email"));

        passwordResetTokenRepository.deleteByUser_Id(user.getId());

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setExpiryDate(Instant.now().plusMillis(RESET_TOKEN_DURATION));
        resetToken.setUsed(false);

        PasswordResetToken savedToken = passwordResetTokenRepository.saveAndFlush(resetToken);
        String resetUrl = "/reset-password.html?token=" + savedToken.getToken();

        return new ForgotPasswordResponse(
                "Password reset link generated. It expires in 15 minutes.",
                savedToken.getToken(),
                resetUrl
        );
    }

    @Transactional
    public String resetPassword(String token, String password) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (resetToken.isUsed()) {
            throw new RuntimeException("Reset token has already been used");
        }

        if (resetToken.getExpiryDate().isBefore(Instant.now())) {
            passwordResetTokenRepository.delete(resetToken);
            throw new RuntimeException("Reset token expired");
        }

        User user = resetToken.getUser();

        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException(
                    "Please enter a new password different from your current password."
            );
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        refreshTokenService.deleteByUserId(user.getId());
        passwordResetTokenRepository.deleteByUser_Id(user.getId());

        return "Password reset successfully";
    }
}
