package com.fpt.form.user;

import com.fpt.enums.Status;
import com.fpt.enums.UserGender;
import com.fpt.validation.form.user.UserEmailNotExists;
import com.fpt.validation.form.user.UserMobileNotExists;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddUserForm {

	@NotEmpty(message = "{UserForm.fullName.NotEmpty}")
	private String fullName;

	@NotNull
	private UserGender gender;

	@NotEmpty(message = "{UserForm.email.NotEmpty}")
	@Email(message = "{UserForm.email.NotEmail}")
	@UserEmailNotExists
	private String email;

	@NotEmpty(message = "{UserForm.mobile.NotEmpty}")
	@UserMobileNotExists
	private String mobile;

	@Positive
	@NotNull
	private Long roleId;

	@NotNull
	private Status status;
}
