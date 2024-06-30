package com.fpt.validation.form.subject;


import com.fpt.service.SubjectService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


public class SubjectNameNotExistsValidator implements ConstraintValidator<SubjectNameNotExists, String> {

	@Autowired
	private SubjectService service;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

		// null values are valid
		if (StringUtils.isEmpty(data)) {
			return false;
		}

		return !service.isSubjectExistsByName(data);
	}
}