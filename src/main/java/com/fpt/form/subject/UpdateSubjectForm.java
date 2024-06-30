package com.fpt.form.subject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateSubjectForm {

	@JsonIgnore
	private Long id;

	@NotBlank
	private String name;

	@PositiveOrZero
	private Integer duration;

	@PositiveOrZero
	private Long facultyId;

	@PositiveOrZero
	private Long managerId;

	private Status status;
}
