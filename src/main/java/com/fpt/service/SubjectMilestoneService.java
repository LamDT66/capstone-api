package com.fpt.service;

import java.util.List;

import com.fpt.entity.SubjectMilestone;
import com.fpt.form.subjectmilestone.AddSubjectMilestoneForm;
import com.fpt.form.subjectmilestone.UpdateSubjectMilestoneForm;

public interface SubjectMilestoneService {
	List<SubjectMilestone> findAllSubjectMilestone(Long subjectId);

	void addSubjectMilestone(AddSubjectMilestoneForm addSubjectMilestoneForm, Long subjectId);

	void updateSubjectMilestone(UpdateSubjectMilestoneForm updateSubjectMilestoneForm, Long id, Long subjectId);

	void deleteSubject(Long id);

	boolean isSubjectMilestoneExistsByTitle(String title);

	boolean isSubjectMilestoneExistsByID(Long id);
}
