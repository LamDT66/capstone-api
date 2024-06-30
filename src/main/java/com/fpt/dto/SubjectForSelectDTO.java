package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectForSelectDTO {

	private Long id;

	@JsonProperty("name")
	private String subjectName;
}
