package com.fpt.form.student;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.Status;
import com.fpt.enums.UserGender;
import com.fpt.validation.form.user.UserEmailNotExists;
import com.fpt.validation.form.user.UserMobileNotExists;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImportStudentForm {

	@JsonIgnore
	private Long classId;

	@NotEmpty
	private List<@Valid StudentForm> students;

	@Data
	@NoArgsConstructor
	public static class StudentForm {
		@NotEmpty(message = "{UserForm.fullName.NotEmpty}")
		private String fullName;

		@NotNull
		private UserGender gender;

		@NotEmpty(message = "{UserForm.email.NotEmpty}")
		@Email(message = "{UserForm.email.NotEmail}")
		@UserEmailNotExists
		private String email;
		
		@JsonIgnore
		private String password;

		@NotEmpty(message = "{UserForm.mobile.NotEmpty}")
		@UserMobileNotExists
		private String mobile;

		@NotNull
		private Status status;
	}
}
