package com.fpt.validation.form.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fpt.service.TokenService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegistrationTokenValidValidator implements ConstraintValidator<RegistrationTokenValid, String> {

	@Autowired
	private TokenService service;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isEmpty(data)) {
			return false;
		}

		return service.isRegistrationTokenValid(data);
	}
}
