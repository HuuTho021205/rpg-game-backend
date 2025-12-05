package com.example.game.controller;

import com.example.game.dto.CharacterRequest;
import com.example.game.entity.GameCharacter;
import com.example.game.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping("/create")
    public ResponseEntity<?> createCharacter(@Valid @RequestBody CharacterRequest request) {
        GameCharacter character = characterService.createCharacter(request.getName(), request.getJobClass());
        // Tạm thời trả về Entity để check
        return ResponseEntity.ok(character);
    }
}