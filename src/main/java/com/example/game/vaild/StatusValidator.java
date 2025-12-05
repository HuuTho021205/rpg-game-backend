package com.example.game.vaild;

import com.example.game.annotation.ValidStatus;
import com.example.game.enums.JobClass;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<ValidStatus,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null){
            return false;
        }
        for (JobClass jobClass : JobClass.values()){
            if (jobClass.name().equalsIgnoreCase(s)){
                return  true;
            }
        }
        return false;
    }
}
