package com.fpt.form.project_setting;

import com.fpt.enums.ProjectSettingType;
import com.fpt.validation.form.projectsetting.ProjectSettingNameNotExists;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddProjectSettingForm {

    private Long projectId;

    @NotNull
    private ProjectSettingType settingType;

    @NotEmpty
    @ProjectSettingNameNotExists
    private String settingName;
}
