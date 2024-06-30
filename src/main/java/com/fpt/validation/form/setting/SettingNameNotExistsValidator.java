package com.fpt.validation.form.setting;
import com.fpt.service.SettingService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


public class SettingNameNotExistsValidator implements ConstraintValidator<SettingNameNotExists, String> {

    @Autowired
    private SettingService service;

    @Override
    public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext){
        // null values are valid
        if (StringUtils.isEmpty(data)) {
            return false;
        }
        return !service.isSettingExistsBySettingName(data);
    }
}
