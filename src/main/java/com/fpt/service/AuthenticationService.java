package com.fpt.service;

import com.fpt.dto.LoginDTO;
import com.fpt.form.authentication.ChangePasswordForm;
import com.fpt.form.authentication.RegisterForm;
import com.fpt.form.authentication.ResetPasswordForm;
import com.fpt.form.authentication.ResetPasswordOtpForm;

public interface AuthenticationService {

	LoginDTO login(String email);

	void register(RegisterForm registerForm);

	void confirmUserAccountRegistrationViaEmail(String token);

	void changePassword(ChangePasswordForm changePasswordForm);

	void sendResetPasswordOTPViaEmail(ResetPasswordOtpForm form);

	void resetPassword(ResetPasswordForm form);
}
