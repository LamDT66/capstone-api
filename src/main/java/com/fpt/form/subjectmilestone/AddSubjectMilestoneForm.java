package com.fpt.form.subjectmilestone;

import com.fpt.validation.form.subjectmilestone.SubjectMilestoneTitleNotExists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AddSubjectMilestoneForm {

	@SubjectMilestoneTitleNotExists
	private String title;

	private int step;

	private int duration;
}