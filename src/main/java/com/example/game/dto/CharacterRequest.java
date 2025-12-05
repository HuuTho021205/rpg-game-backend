package com.example.game.dto;

import com.example.game.annotation.ValidStatus;
import com.example.game.enums.JobClass;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CharacterRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;
    @ValidStatus(message = "Chức nghiệp không hợp lệ (WARRIOR, MAGE, ARCHER)")
    private String jobClass;
}
