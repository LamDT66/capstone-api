package com.fpt.form.issue;

import com.fpt.enums.IssueStatus;
import com.fpt.validation.form.user.UserIDExists;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddIssueForm {

	@Positive
	private Long milestoneId;

	@NotBlank
	private String title;

	@NotBlank
	private String description;

	@Positive
	private Long typeId;

	@Positive
	private Long processId;

	@Positive
	@UserIDExists
	private Long assigneeId;

	private IssueStatus status;

}
