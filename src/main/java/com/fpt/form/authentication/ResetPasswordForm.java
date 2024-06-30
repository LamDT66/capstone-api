package com.fpt.form.authentication;

import com.fpt.validation.form.authentication.ResetPasswordOtpValid;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordForm {

	@NotBlank
	@ResetPasswordOtpValid
	private String otp;

	@NotBlank
	private String newPassword;
}
