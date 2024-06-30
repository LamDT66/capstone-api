package com.fpt.validation.form.setting;

import com.fpt.service.SettingService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class SettingIDExistsValidator implements ConstraintValidator<SettingIDExists, Long> {

    @Autowired
    private SettingService service;

    @Override
    public boolean isValid(Long data, ConstraintValidatorContext constraintValidatorContext) {

        if (data <= 0) {
            return false;
        }
        return service.isSettingExistsByID(data);
    }
}
