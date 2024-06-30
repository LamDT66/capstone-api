package com.fpt.form.subject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.Status;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class  FilterSubjectForm {

	private Status status;

	@JsonIgnore
	private Long managerId;

	@PositiveOrZero
	private Long teacherId;

}
