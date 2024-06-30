package com.fpt.validation.form.subjectmilestone;

import com.fpt.service.SubjectMilestoneService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class SubjectMilestoneIDExistsValidator implements ConstraintValidator<SubjectMilestoneIDExists, Long> {

	@Autowired
	private SubjectMilestoneService service;

	@Override
	public boolean isValid(Long data, ConstraintValidatorContext constraintValidatorContext) {

		if (data <= 0) {
			return false;
		}
		return service.isSubjectMilestoneExistsByID(data);
	}
}