package com.fpt.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.config.internationalization.MessageProperty;
import com.fpt.dto.LoginDTO;
import com.fpt.dto.UserBasicInformationDTO;
import com.fpt.entity.Setting;
import com.fpt.entity.Token;
import com.fpt.entity.User;
import com.fpt.enums.Status;
import com.fpt.enums.TokenType;
import com.fpt.form.authentication.ChangePasswordForm;
import com.fpt.form.authentication.RegisterForm;
import com.fpt.form.authentication.ResetPasswordForm;
import com.fpt.form.authentication.ResetPasswordOtpForm;
import com.fpt.repository.TokenRepository;
import com.fpt.repository.UserRepository;
import com.fpt.service.AuthenticationService;
import com.fpt.service.EmailService;
import com.fpt.service.TokenService;
import com.fpt.service.UserService;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MessageProperty messageProperty;

	@Override
	public LoginDTO login(String email) {
		// get entity
		User user = userService.getUserByEmail(email);

		// convert entity to dto
		UserBasicInformationDTO userInfoDTO = modelMapper.map(user, UserBasicInformationDTO.class);

		// return user info & JWT token
		return LoginDTO.builder()
				.token(tokenService.createJWTToken(email)) // create JWT token
				.refreshToken(tokenService.createJWTRefreshToken(user).getToken()) // create JWT refresh token
				.basicInformationOfUser(userInfoDTO)
				.build();
	}

	@Override
	public void register(RegisterForm form) {
		// create new user
		User newUser = User.builder()
			.email(form.getEmail())
			.fullName(form.getFullName())
			.status(Status.INACTIVE)
			.password(passwordEncoder.encode(form.getPassword()))
			.role(Setting.builder().id(4L).build())
			.build();
		newUser = userRepository.save(newUser);
		
		// create token to active user
		Token newToken = tokenService.createRegistrationToken(newUser);

		// sending mail
		emailService.sendEmail(
				newUser.getEmail(), 
				messageProperty.getMessage("email.send.registration.subject.title"),
				"EMAIL VERIFICATION\n\n" +
				messageProperty.getMessage("email.send.registration.subject.body")
				+ "\n\n"
				+ "http://localhost:8080/api/v1/auth/confirm-account?token=" + newToken.getToken());
	}

	@Override
	public void confirmUserAccountRegistrationViaEmail(String token) {
		// active user
		Token tokenEntity = tokenRepository.findByTokenAndTokenType(token, TokenType.REGISTRATION);
		User user = tokenEntity.getUser();
		user.setStatus(Status.ACTIVE);
		userRepository.save(user);

		// delete token
		tokenRepository.delete(tokenEntity);
	}
	
	@Override
	public void changePassword(ChangePasswordForm changePasswordForm) {
		User user = userService.getCurrentLoginUser();
		user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
		userRepository.save(user);
	}
	
	@Override
	public void sendResetPasswordOTPViaEmail(ResetPasswordOtpForm form) {
		
		User user = userRepository.findUserByEmail(form.getEmail());
		
		// create token to active user
		Token newToken = tokenService.createResetPasswordOTP(user);

		// sending mail
		emailService.sendEmail(
				user.getEmail(), 
				messageProperty.getMessage("email.send.reset-password.subject.title"),
				messageProperty.getMessage("email.send.reset-password.subject.body") + newToken.getToken());
	}

	@Override
	public void resetPassword(ResetPasswordForm form) {
		Token token = tokenRepository.findByToken(form.getOtp());
		
		// change password
		User user = token.getUser();
		user.setPassword(passwordEncoder.encode(form.getNewPassword()));
		userRepository.save(user);
		
		// delete token
		tokenRepository.delete(token);
	}
}
