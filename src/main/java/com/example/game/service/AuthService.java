package com.example.game.service;


import com.example.game.dto.AuthRequest;
import com.example.game.dto.UserResponse;
import com.example.game.entity.User;
import com.example.game.repository.UserRepo;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(AuthRequest request){
        if (userRepo.existsByUsername(request.getUsername())){
            throw new RuntimeException("Tài khoản đã tồn tại");
        }
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("USER");
        newUser.setEmail(request.getEmail());
        newUser.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepo.save(newUser);
        return userMapper.toDTO(savedUser);
    }
}
