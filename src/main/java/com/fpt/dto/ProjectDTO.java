package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDTO {

	private Long id;

	private String englishName;

	@JsonProperty("className")
	private String clazzName;

	@JsonProperty("semesterName")
	private String clazzSemesterSettingName;
}
