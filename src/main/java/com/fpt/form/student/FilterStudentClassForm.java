package com.fpt.form.student;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class FilterStudentClassForm {

	@JsonIgnore
	private Long classId;
}
