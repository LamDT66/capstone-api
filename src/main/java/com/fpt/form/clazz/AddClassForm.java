package com.fpt.form.clazz;

import java.util.Date;

import com.fpt.enums.Status;
import com.fpt.validation.form.clazz.ClassNameNotExists;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddClassForm {

	@NotBlank
	@ClassNameNotExists
	private String name;

	@Positive
	private Long subjectId;

	@NotNull
	private Date startDate;

	@NotNull
	@Future
	private Date endDate;

	@Positive
	private Long semesterId;

	@NotNull
	private Status status;

}
