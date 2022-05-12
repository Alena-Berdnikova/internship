package com.game.entity.Validation;

import com.game.entity.Validation.CreateValidationGroup;
import com.game.entity.Player;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

public class PlayerValidator implements SmartValidator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Player.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validate(target, errors, null);
    }

    @Override
    public void validate(Object target, Errors errors, Object... groups) {
        if (target == null) {
            errors.reject("null");
            return;
        }
        Player player = (Player) target;
        if (groups!=null&&groups.length>0&&groups[0] == CreateValidationGroup.class) {
            if (player.getName() == null) {
                errors.rejectValue("name", "name is null");
            }
            if (player.getTitle() == null) {
                errors.rejectValue("title", "title is null");
            }
            if (player.getRace() == null) {
                errors.rejectValue("race", "race is null");
            }
            if (player.getProfession() == null) {
                errors.rejectValue("profession", "profession is null");
            }
            if (player.getBirthday() == null) {
                errors.rejectValue("birthday", "birthday is null");
            }
            if (player.getExperience() == null) {
                errors.rejectValue("experience", "experience is null");
            }
        }
        if (player.getTitle() != null && player.getTitle().length() > 30) {
            errors.rejectValue("title", "invalid title");
        }
        if (player.getName() != null && (player.getName().isEmpty() || player.getName().length() > 12)) {
            errors.rejectValue("name", "invalid name");
        }
        if (player.getExperience() != null && (player.getExperience() < 0 || player.getExperience() > 10_000_000)) {
            errors.rejectValue("name", "empty name");
        }
        if (player.getBirthday() != null && player.getBirthday().getTime() < 0) {
            errors.rejectValue("birthday", "invalid birthday");
        }
    }
}
