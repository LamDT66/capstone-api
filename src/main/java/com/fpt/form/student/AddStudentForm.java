package com.fpt.form.student;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddStudentForm {

	@JsonIgnore
	private Long classId;

	@NotEmpty
	private List<@Positive Long> studentIds;

}
