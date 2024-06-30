package com.fpt.form.milestone_project;

import java.util.Date;

import com.fpt.enums.MilestoneStatus;
import com.fpt.validation.form.projectmilestone.ProjectMilestoneTitleNotExists;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class AddMilestoneForm {

	@PositiveOrZero
	private Long projectId;

	@NotBlank
	@ProjectMilestoneTitleNotExists
	private String title;

	@NotNull
	private Date startDate;

	@NotNull
	@Future
	private Date endDate;

	@NotNull
	private MilestoneStatus status;
}
