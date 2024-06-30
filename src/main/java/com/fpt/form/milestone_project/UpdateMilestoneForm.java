package com.fpt.form.milestone_project;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.enums.MilestoneStatus;
import com.fpt.validation.form.projectmilestone.ProjectMilestoneTitleNotExists;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateMilestoneForm {

	@JsonIgnore
	private Long id;

	@NotBlank
	private String title;

	@NotNull
	private Date startDate;

	@NotNull
	@Future
	private Date endDate;

	@NotNull
	private MilestoneStatus status;

}
