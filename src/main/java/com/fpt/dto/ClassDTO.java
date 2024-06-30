package com.fpt.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpt.enums.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDTO {

	private Long id;

	private String name;

	private Date startDate;

	private Date endDate;

	@JsonProperty("semesterId")
	private Long semesterId;

	@JsonProperty("semesterName")
	private String semesterSettingName;

	private Long subjectId;

	@JsonProperty("subjectName")
	private String subjectSubjectName;

	private Status status;
}
