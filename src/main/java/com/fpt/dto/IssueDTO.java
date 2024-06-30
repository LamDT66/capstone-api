package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpt.enums.IssueStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IssueDTO {

	private Long id;

	private String title;

	private String projectEnglishName;

	private String projectVietnameseName;

	@JsonProperty("milestone")
	private String milestoneTitle;

	@JsonProperty("typeName")
	private String typeSettingName;

	@JsonProperty("processName")
	private String processSettingName;

	private IssueStatus status;

	private String assigneeFullName;

	private String assigneeEmail;

	private String assignerFullName;

	private String assignerEmail;
}
