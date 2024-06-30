package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO {

	@JsonProperty("id")
	private Long studentId;

	@JsonProperty("fullName")
	private String studentFullName;

	@JsonProperty("gender")
	private String studentGender;

	@JsonProperty("email")
	private String studentEmail;

	@JsonProperty("mobile")
	private String studentMobile;

	@JsonProperty("status")
	private String studentStatus;
}
