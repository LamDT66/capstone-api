package com.fpt.form.issue;

import com.fpt.enums.IssueStatus;
import com.fpt.validation.form.user.UserIDExists;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FilterIssueForm {

	@Positive
	private Long projectId;

	@Positive
	@UserIDExists
	private String assignerId;

	@Positive
	@UserIDExists
	private String assigneeId;

	@Positive
	private Long teacherId;

	@Positive
	private Long studentId;

	private IssueStatus status;
}
