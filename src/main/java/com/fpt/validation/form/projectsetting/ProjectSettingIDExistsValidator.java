package com.fpt.validation.form.projectsetting;

import com.fpt.service.ProjectSettingService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectSettingIDExistsValidator implements ConstraintValidator<ProjectSettingIDExists, Long> {

    @Autowired
    private ProjectSettingService service;

    @Override
    public boolean isValid(Long data, ConstraintValidatorContext constraintValidatorContext) {

        if (data <= 0) {
            return false;
        }
        return service.isProjectSettingExistsByID(data);
    }
}
