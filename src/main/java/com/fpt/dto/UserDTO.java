package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String fullName;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String gender;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String mobile;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("roleName")
	private String roleSettingName;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String roleId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String status;
}
