package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class SettingDTO {

	private Long id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String settingType;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String settingName;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String settingDisplayOrder;
}
