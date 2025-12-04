package com.example.game.controller;

import com.example.game.dto.AuthRequest;
import com.example.game.dto.UserResponse;
import com.example.game.service.AuthService;
import com.example.game.service.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper mapService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody AuthRequest request){
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
}
