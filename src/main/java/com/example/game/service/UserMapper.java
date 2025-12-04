package com.example.game.service;


import com.example.game.dto.UserResponse;
import com.example.game.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    // chuyển entity sang dto
    public UserResponse toDTO(User entity){
        UserResponse dto = new UserResponse();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setRole(entity.getRole());
        return dto;
    }
}
