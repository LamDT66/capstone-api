package com.fpt.validation.form.projectsetting;

import com.fpt.service.ProjectSettingService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class ProjectSettingNameNotExistsValidator implements ConstraintValidator<ProjectSettingNameNotExists, String> {

    @Autowired
    private ProjectSettingService service;

    @Override
    public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {
        // null values are valid
        if(StringUtils.isEmpty(data)) {
            return false;
        }
//        return !service.isProjectSettingExistsBySettingName(data);
        return false;
    }
}
