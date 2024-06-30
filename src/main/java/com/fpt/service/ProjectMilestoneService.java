package com.fpt.service;

import com.fpt.entity.Milestone;
import com.fpt.form.milestone_project.AddMilestoneForm;
import com.fpt.form.milestone_project.UpdateMilestoneForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectMilestoneService {

    Page<Milestone> getAllProjectMilestones(Long id, Pageable pageable, String search);

    Milestone getProjectMilestoneByID(Long id);

    void createProjectMilestone(AddMilestoneForm milestoneForm);

    void updateProjectMilestone(UpdateMilestoneForm milestoneForm);

    void deleteProjectMilestone(Long id);

    boolean isProjectMilestoneExistsByID(Long id);

    boolean isProjectMilestoneExistsByTitle(String title, Long projectId);
}
