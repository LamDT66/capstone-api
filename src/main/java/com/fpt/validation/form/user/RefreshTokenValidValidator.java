package com.fpt.validation.form.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fpt.service.TokenService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RefreshTokenValidValidator implements ConstraintValidator<RefreshTokenValid, String> {

	@Autowired
	private TokenService tokenService;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String refreshToken, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isEmpty(refreshToken)) {
			return false;
		}

		return tokenService.isJWTRefreshTokenValid(refreshToken);
	}
}
