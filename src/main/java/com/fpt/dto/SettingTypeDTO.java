package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SettingTypeDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("name")
	private String settingName;
}
