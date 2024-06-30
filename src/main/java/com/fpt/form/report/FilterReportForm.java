package com.fpt.form.report;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FilterReportForm {

	@PositiveOrZero
	private Long projectId;

	@JsonIgnore
	private Long teacherId;

	@JsonIgnore
	private Long studentId;
}
