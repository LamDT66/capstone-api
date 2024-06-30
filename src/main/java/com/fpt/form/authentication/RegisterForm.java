package com.fpt.form.authentication;

import com.fpt.validation.form.user.UserEmailNotExists;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

	@NotBlank
	private String fullName;

	@NotBlank
	@UserEmailNotExists
	private String email;

	@NotBlank
	private String password;
}
