package com.fpt.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.enums.MilestoneStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MilestoneDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String title;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date startDate;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date endDate;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private MilestoneStatus status;
}
