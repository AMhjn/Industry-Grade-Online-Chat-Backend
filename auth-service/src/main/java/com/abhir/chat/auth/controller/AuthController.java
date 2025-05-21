package com.abhir.chat.auth.controller;

import com.abhir.chat.auth.dto.AuthRequest;
import com.abhir.chat.auth.dto.AuthResponse;
import com.abhir.chat.auth.dto.SignupRequest;
import com.abhir.chat.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequest request) {
        System.out.println("➡️ Incoming signup request: {}" + request);
        authService.register(request);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("You are authenticated!");
    }
}
