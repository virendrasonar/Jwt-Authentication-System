package com.auth.authproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth.authproject.entity.User;
import com.auth.authproject.repository.UserRepository;

@Configuration
public class AdminBootstrapConfig {
    private static final String DEMO_ADMIN_EMAIL = "admin@example.com";
    private static final String DEMO_ADMIN_PASSWORD = "Admin@123";

    @Bean
    CommandLineRunner adminBootstrap(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     @Value("${app.admin.name:Admin}") String adminName,
                                     @Value("${app.admin.email:}") String adminEmail,
                                     @Value("${app.admin.password:}") String adminPassword) {
        return args -> {
            String resolvedAdminEmail = adminEmail.isBlank() ? DEMO_ADMIN_EMAIL : adminEmail;
            String resolvedAdminPassword = adminPassword.isBlank() ? DEMO_ADMIN_PASSWORD : adminPassword;

            User admin = userRepository.findByEmail(resolvedAdminEmail).orElseGet(User::new);
            admin.setName(adminName);
            admin.setEmail(resolvedAdminEmail);
            admin.setPassword(passwordEncoder.encode(resolvedAdminPassword));
            admin.setRole("ADMIN");
            userRepository.save(admin);
        };
    }
}
