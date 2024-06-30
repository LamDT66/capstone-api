package com.fpt.validation.form.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fpt.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserMobileNotExistsValidator implements ConstraintValidator<UserMobileNotExists, String> {

	@Autowired
	private UserService service;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isEmpty(data)) {
			return false;
		}
		return !service.isUserExistsByMobile(data);
	}
}
