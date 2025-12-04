package com.example.game.repository;

import com.example.game.entity.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCharacterRepo extends JpaRepository<GameCharacter,Long> {
}
