package com.fpt.validation.form.subjectmilestone;


import com.fpt.service.SubjectMilestoneService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


public class SubjectMilestoneTitleNotExistsValidator implements ConstraintValidator<SubjectMilestoneTitleNotExists, String> {

	@Autowired
	private SubjectMilestoneService service;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

		// null values are valid
		if (StringUtils.isEmpty(data)) {
			return false;
		}

		return !service.isSubjectMilestoneExistsByTitle(data);
	}
}