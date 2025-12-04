package com.example.game.service;


import com.example.game.dto.AuthRequest;
import com.example.game.dto.AuthResponse;
import com.example.game.dto.LoginRequest;
import com.example.game.dto.UserResponse;
import com.example.game.entity.User;
import com.example.game.repository.UserRepo;


import com.example.game.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

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

    public AuthResponse login(LoginRequest request){
        //1. Xác thực username/password
        // Hàm này sẽ tự động gọi UserDetailsService để load user và so sánh password (đã mã hóa)
        // Nếu sai pass -> Nó tự ném lỗi (BadCredentialsException)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // tạo token trả cho user khi đăng nhap thành công
        var user = userRepo.findByUsername(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtUtils.genToken(user.getUsername());
        return new AuthResponse(jwtToken);
    }
}
