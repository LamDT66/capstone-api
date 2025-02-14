package com.fpt.form.project;

import java.util.List;

import com.fpt.enums.ProjectStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddProjectForm {

	@Positive
	private Long classId;

	@NotBlank
	private String groupName;

	@NotBlank
	private String projectCode;

	@NotBlank
	private String vietnameseName;

	@NotBlank
	private String englishName;

	@Positive
	private Long mentorId;

	@Positive
	private Long coMentorId;

	@Positive
	private Long leaderId;

	@NotNull
	private ProjectStatus status;

	private String note;

	@NotEmpty
	private List<@Positive Long> teamMemberIds;
}
