package com.example.game.service;

import com.example.game.entity.GameCharacter;
import com.example.game.entity.User;
import com.example.game.enums.JobClass;
import com.example.game.repository.GameCharacterRepo;
import com.example.game.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final UserRepo userRepo;
    private final GameCharacterRepo gameCharacterRepo;

    // khai báo chỉ số ban đầu
    private static final int STARTING_LEVEL = 1;
    private static final Long STARTING_EXP = 0L;
    private static final Long STARTING_GOLD = 1000L;

    private static final int WARRIOR_MAX_HP = 150;
    private static final int WARRIOR_ATTACK = 15;
    private static final int WARRIOR_DEFENSE = 20;

    private static final int MAGE_MAX_HP = 80;
    private static final int MAGE_ATTACK = 30;
    private static final int MAGE_DEFENSE = 8;

    private static final int ARCHER_MAX_HP = 110;
    private static final int ARCHER_ATTACK = 20;
    private static final int ARCHER_DEFENSE = 12;

    public GameCharacter createCharacter (String charracterName, String jobClassStr){
        // xác định chủ sở hữu từ jwt token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // kiểm tra xem user có tồn tại hay không
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("User không tồn tại"));

        // kiểm tra qui tắc 1 user 1 character
        if (gameCharacterRepo.existsByUserId(user.getId())){
            throw new IllegalStateException("User đã có nhân vật. Không thể tạo mới");
        }
        JobClass jobClass = JobClass.valueOf(jobClassStr.toUpperCase());
        // tạo mới nhân vật
        GameCharacter character = new GameCharacter();
        character.setUser(user);
        character.setName(charracterName);
        character.setJobClass(jobClass.name());

        character.setLevel(STARTING_LEVEL);
        character.setExp(STARTING_EXP);
        character.setGold(STARTING_GOLD);

        setBaseStats(character, jobClass);
        return gameCharacterRepo.save(character);
    }

    private void setBaseStats(GameCharacter character, JobClass jobClass) {
        switch (jobClass){
            case MAGE -> {
                character.setMaxHp(MAGE_MAX_HP);
                character.setHp(MAGE_MAX_HP);
                character.setAttack(MAGE_ATTACK);
                character.setDefense(MAGE_DEFENSE);
                break;
            }
            case WARRIOR -> {
                character.setMaxHp(WARRIOR_MAX_HP);
                character.setHp(WARRIOR_MAX_HP);
                character.setAttack(WARRIOR_ATTACK);
                character.setDefense(WARRIOR_DEFENSE);
                break;
            }
            case ACHER -> {
                character.setMaxHp(ARCHER_MAX_HP);
                character.setHp(ARCHER_MAX_HP);
                character.setAttack(ARCHER_ATTACK);
                character.setDefense(ARCHER_DEFENSE);
                break;
            }

            default ->
                throw new IllegalArgumentException("JobClass không hợp lệ: " + jobClass);

        }

    }
}
