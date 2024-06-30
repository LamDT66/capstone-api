package com.fpt.form.authentication;

import com.fpt.validation.form.user.UserEmailExists;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordOtpForm {

	@NotBlank
	@UserEmailExists
	private String email;
}
