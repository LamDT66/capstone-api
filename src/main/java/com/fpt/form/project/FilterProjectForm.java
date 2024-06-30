package com.fpt.form.project;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FilterProjectForm {

	@Positive
	private Long classId;

	@JsonIgnore
	private Long managerId;

	@Positive
	private Long teacherId;

	@Positive
	private Long studentId;
}
