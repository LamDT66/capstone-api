package com.fpt.service;

import java.util.List;

import com.fpt.entity.Milestone;

public interface MilestoneService {

	List<Milestone> getMilestoneByStudent();
	
	List<Milestone> getMilestoneByClassIdOrderByStartDate(Long id);

	List<Milestone> getMilestoneByProjectIdOrderByStartDate(Long id);

	boolean isMilestoneExistsByID(Long id);

	void createMilestone(Milestone milestone);

	void updateMilestone(Milestone milestone);

	void deleteMilestone(Long id);

	Milestone getMilestoneById(Long id);

	boolean isMilestoneExistsByTitle(String title);
}
