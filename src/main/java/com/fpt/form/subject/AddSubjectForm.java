package com.fpt.form.subject;

import com.fpt.enums.Status;
import com.fpt.validation.form.subject.SubjectNameNotExists;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddSubjectForm {

	@SubjectNameNotExists
	private String name;

	@PositiveOrZero
	private Integer duration;

	@PositiveOrZero
	private Long facultyId;

	@PositiveOrZero
	private Long managerId;

	private Status status;
}