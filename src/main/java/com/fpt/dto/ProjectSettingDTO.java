package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ProjectSettingDTO {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String settingType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String settingName;
}
