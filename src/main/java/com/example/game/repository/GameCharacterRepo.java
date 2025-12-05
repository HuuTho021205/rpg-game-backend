package com.example.game.repository;

import com.example.game.entity.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameCharacterRepo extends JpaRepository<GameCharacter,Long> {
    Optional<GameCharacter> findByUserId(String userId);

    boolean existsByUserId(Long userId);
}
