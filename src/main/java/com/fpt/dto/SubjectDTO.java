package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectDTO {

	private Long id;

	@JsonProperty("name")
	private String subjectName;

	@JsonProperty("duration")
	private Integer verifyDuration;

	@JsonProperty("facultyId")
	private String facultyId;
	
	@JsonProperty("faculty")
	private String facultySettingName;

	private String managerId;
	
	private String managerFullName;

	private String managerEmail;

	private String status;
}
