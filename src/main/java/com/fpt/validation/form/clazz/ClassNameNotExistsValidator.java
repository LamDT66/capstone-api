package com.fpt.validation.form.clazz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fpt.service.ClassService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClassNameNotExistsValidator implements ConstraintValidator<ClassNameNotExists, String> {

	@Autowired
	private ClassService service;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

		// null values are valid
		if (StringUtils.isEmpty(data)) {
			return false;
		}

		return !service.isClassExistsByName(data);
	}
}