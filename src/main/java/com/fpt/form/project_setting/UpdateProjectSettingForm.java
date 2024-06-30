package com.fpt.form.project_setting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.ProjectSettingType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProjectSettingForm {

    @JsonIgnore
    private Long id;

    private Long projectId;

    @NotNull
    private ProjectSettingType settingType;

    @NotEmpty
    private String settingName;
}
