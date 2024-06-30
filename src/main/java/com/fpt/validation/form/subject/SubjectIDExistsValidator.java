package com.fpt.validation.form.subject;

import org.springframework.beans.factory.annotation.Autowired;

import com.fpt.service.SubjectService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SubjectIDExistsValidator implements ConstraintValidator<SubjectIDExists, Long> {

	@Autowired
	private SubjectService service;

	@Override
	public boolean isValid(Long data, ConstraintValidatorContext constraintValidatorContext) {

		if (data <= 0) {
			return false;
		}

		return service.isSubjectExistsByID(data);
	}
}