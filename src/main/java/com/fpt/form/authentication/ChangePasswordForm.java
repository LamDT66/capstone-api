package com.fpt.form.authentication;

import com.fpt.validation.form.authentication.OldPasswordMatch;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordForm {

	@NotBlank
	@OldPasswordMatch
	private String oldPassword;

	@NotBlank
	private String newPassword;
}
