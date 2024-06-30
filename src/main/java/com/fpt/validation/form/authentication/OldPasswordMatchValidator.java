package com.fpt.validation.form.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fpt.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OldPasswordMatchValidator implements ConstraintValidator<OldPasswordMatch, String> {

	@Autowired
	private UserService userService;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isEmpty(data)) {
			return false;
		}

		return userService.isOldPasswordMatch(data);
	}
}
