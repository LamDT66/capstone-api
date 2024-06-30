package com.fpt.form.profile;

import com.fpt.entity.Setting;
import com.fpt.enums.UserGender;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateProfileForm {

	@NotEmpty
	private String fullName;

	@NotNull
	private UserGender gender;

	@NotNull
	private Long facultyId;

	@NotEmpty
	private String mobile;
}
