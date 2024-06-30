package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserBasicInformationDTO {

	private Long id;

	private String fullName;

	private String email;

	private String mobile;

	private String gender;

	private String avatar;

	private String roleId;

	@JsonProperty("roleName")
	private String roleSettingName;

	private String facultyId;

	@JsonProperty("facultyName")
	private String facultySettingName;
}