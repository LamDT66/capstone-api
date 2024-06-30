package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String title;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long projectId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long projectLeaderId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String projectEnglishName;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime createdAt;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String fileUrl;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String milestoneTitle;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String creatorFullName;
}
