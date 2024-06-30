package com.fpt.service;

import org.springframework.security.core.Authentication;

import com.fpt.dto.TokenDTO;
import com.fpt.entity.Token;
import com.fpt.entity.User;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {

	boolean isTokenExists(String token);
	
	boolean isRegistrationTokenValid(String token);

	Token createRegistrationToken(User user);
	
	Token createResetPasswordOTP(User user);
	
	boolean isResetPasswordOtpValid(String otp);
	
	String createJWTToken(String email);

	Authentication parseTokenToUserInformation(HttpServletRequest request);

	Token createJWTRefreshToken(User user);

	boolean isJWTRefreshTokenValid(String refreshToken);

	TokenDTO exchangeNewToken(String refreshToken);
	
	
}
