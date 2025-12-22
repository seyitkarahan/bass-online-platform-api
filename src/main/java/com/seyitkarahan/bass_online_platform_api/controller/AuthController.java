package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.UserLoginRequest;
import com.seyitkarahan.bass_online_platform_api.dto.request.UserRegisterRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.AuthResponse;
import com.seyitkarahan.bass_online_platform_api.dto.response.UserResponse;
import com.seyitkarahan.bass_online_platform_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody @Valid UserRegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid UserLoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
