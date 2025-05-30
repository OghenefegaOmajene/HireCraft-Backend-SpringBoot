package com.example.hirecraft.controllers;

import com.example.hirecraft.dtos.requests.LoginRequest;
import com.example.hirecraft.dtos.requests.SignUpRequestWrapper;
import com.example.hirecraft.dtos.response.LoginResponse;
import com.example.hirecraft.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid SignUpRequestWrapper requestWrapper) {
        return ResponseEntity.ok(authService.register(requestWrapper));
    }
}
