package com.fpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.config.exception.BusinessException;
import com.fpt.config.internationalization.MessageProperty;
import com.fpt.dto.LoginDTO;
import com.fpt.dto.TokenDTO;
import com.fpt.entity.User;
import com.fpt.enums.Status;
import com.fpt.form.authentication.ChangePasswordForm;
import com.fpt.form.authentication.LoginForm;
import com.fpt.form.authentication.RegisterForm;
import com.fpt.form.authentication.ResetPasswordForm;
import com.fpt.form.authentication.ResetPasswordOtpForm;
import com.fpt.service.AuthenticationService;
import com.fpt.service.TokenService;
import com.fpt.service.UserService;
import com.fpt.validation.form.user.RefreshTokenValid;
import com.fpt.validation.form.user.RegistrationTokenValid;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private MessageProperty messageProperty;

	@PostMapping("/login")
	public LoginDTO login(@RequestBody @Valid LoginForm loginForm) throws BusinessException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginForm.getEmail(), 
						loginForm.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = userService.getUserByEmail(loginForm.getEmail());
        if(user.getStatus() == Status.INACTIVE) throw new BusinessException("User is inactive");

		return authenticationService.login(loginForm.getEmail());
	}
	
	@GetMapping("/refreshToken")
	public TokenDTO refreshtoken(@RefreshTokenValid String refreshToken) {
		return tokenService.exchangeNewToken(refreshToken);
	}

	@PostMapping(value = "/register")
	public void register(@Valid @RequestBody RegisterForm registerForm) {
		authenticationService.register(registerForm);
	}

	@GetMapping(value = "/confirm-account")
	public String confirmUserAccountRegistration(
			@RegistrationTokenValid @RequestParam("token") String token) {
		authenticationService.confirmUserAccountRegistrationViaEmail(token);
		return messageProperty.getMessage("account.email.verify.success");
	}
	
	@GetMapping("/old-password-match")
	public boolean isOldPasswordMatch(String oldPassword) {
		return userService.isOldPasswordMatch(oldPassword);
	}

	@PostMapping("/change-password")
	public void changePassword(@Valid @RequestBody ChangePasswordForm changePasswordForm) {
		authenticationService.changePassword(changePasswordForm);
	}

	@PostMapping("/reset-password-otp")
	public void resetPassword(@Valid @RequestBody ResetPasswordOtpForm form) {
		authenticationService.sendResetPasswordOTPViaEmail(form);
	}

	@GetMapping("/reset-password-otp/exists")
	public boolean isResetPasswordOtpValid(String otp) {
		return tokenService.isResetPasswordOtpValid(otp);
	}
	
	@PostMapping("/reset-password")
	public void resetPassword(@Valid @RequestBody ResetPasswordForm form) {
		authenticationService.resetPassword(form);
	}
}
