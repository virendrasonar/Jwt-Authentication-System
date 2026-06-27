package com.auth.authproject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @GetMapping("/dashboard")
    public Map<String, String> dashboard(Authentication authentication) {

        Map<String, String> response = new HashMap<>();

        response.put("username", authentication.getName());
        response.put("message", "Welcome to JWT Authentication System!");

        return response;
    }
}