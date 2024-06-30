package com.fpt.form.clazz;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateClassForm {

	@JsonIgnore
	private Long id;

	@NotBlank
	private String name;

	@NotNull
	private Date startDate;

	@NotNull
	private Date endDate;

	@PositiveOrZero
	private Long semesterId;

	private Status status;

}
