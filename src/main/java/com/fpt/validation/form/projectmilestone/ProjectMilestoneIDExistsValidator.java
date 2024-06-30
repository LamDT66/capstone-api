package com.fpt.validation.form.projectmilestone;


import com.fpt.service.ProjectMilestoneService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectMilestoneIDExistsValidator implements ConstraintValidator<ProjectMilestoneIDExists, Long> {

    @Autowired
    private ProjectMilestoneService service;

    @Override
    public boolean isValid(Long data, ConstraintValidatorContext constraintValidatorContext) {

        if (data <= 0) {
            return false;
        }
        return service.isProjectMilestoneExistsByID(data);
    }
}
