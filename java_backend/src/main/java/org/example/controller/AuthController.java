package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.config.security.JwtService;
import org.example.dto.user.UserAuthDto;
import org.example.dto.user.UserRegisterDto;
import org.example.entities.UserEntity;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto user) {
        try {
            userService.register(user);
            return ResponseEntity.ok(Map.of("message", "User successfully registered!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthDto user) {
        try {
            String token = userService.authenticate(user);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login error: " + e.getMessage());
        }
    }
}
