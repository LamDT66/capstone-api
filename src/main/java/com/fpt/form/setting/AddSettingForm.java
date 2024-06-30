package com.fpt.form.setting;

import com.fpt.enums.SettingType;
import com.fpt.validation.form.setting.SettingNameNotExists;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddSettingForm {

	@NotNull
	private SettingType settingType;

	@NotEmpty
	@SettingNameNotExists
	private String settingName;
	
	@Positive
	private Integer settingDisplayOrder;
}
