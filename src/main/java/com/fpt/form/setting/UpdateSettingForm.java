package com.fpt.form.setting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.SettingType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateSettingForm {

	@JsonIgnore
	private Long id;

	@NotNull
	private SettingType settingType;

	@NotEmpty
	private String settingName;

	@Positive
	private Integer settingDisplayOrder;
}
