package com.fpt.form.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.Status;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserForm {

	@JsonIgnore
	private Long id;

	@NotNull
	private Long roleId;

	@NotNull
	private Status status;
}
