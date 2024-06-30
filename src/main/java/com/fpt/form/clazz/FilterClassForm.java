package com.fpt.form.clazz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.Status;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FilterClassForm {

	@PositiveOrZero
	private Long subjectId;

	@PositiveOrZero
	private Long semesterId;

	@JsonIgnore
	private Long managerId;

	private Status status;
}
