package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class IssueDetailDTO extends IssueDTO {

	private String description;

	@JsonProperty("milestoneId")
	private String milestoneId;

	@JsonProperty("typeId")
	private String typeId;

	@JsonProperty("processId")
	private String processId;

	private String assigneeId;
}
