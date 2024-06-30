package com.fpt.validation.form.projectmilestone;

import com.fpt.service.ProjectMilestoneService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class ProjectMilestoneTitleNotExistsValidator implements ConstraintValidator<ProjectMilestoneTitleNotExists, String> {

    @Autowired
    private ProjectMilestoneService service;

    @Override
    public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {
        // null values are valid
        if(StringUtils.isEmpty(data)) {
            return false;
        }
        return true;
       // return !service.isProjectMilestoneExistsByTitle(data);
    }
}

