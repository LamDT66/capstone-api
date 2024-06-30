package com.fpt.service.impl;

import com.fpt.entity.Milestone;
import com.fpt.entity.Project;
import com.fpt.entity.User;
import com.fpt.enums.UserRole;
import com.fpt.form.milestone_project.AddMilestoneForm;
import com.fpt.form.milestone_project.UpdateMilestoneForm;
import com.fpt.repository.MilestoneRepository;
import com.fpt.repository.ProjectRepository;
import com.fpt.service.ProjectMilestoneService;
import com.fpt.service.UserService;
import com.fpt.specification.milestone_project.ProjectMilestoneSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class ProjectMilestoneServiceImpl implements ProjectMilestoneService {

    @Autowired
    private UserService userService;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Page<Milestone> getAllProjectMilestones(Long projectId, Pageable pageable, String search) {
        Specification<Milestone> where = ProjectMilestoneSpecification.buildWhere(projectId, search);
        return milestoneRepository.findAll(where, pageable);
    }

    @Override
    public Milestone getProjectMilestoneByID(Long id) {
        return milestoneRepository.findById(id).get();
    }

    @Override
    public void createProjectMilestone(AddMilestoneForm milestoneForm) {
        Milestone milestone = milestoneRepository.save(Milestone.builder()
                .title(milestoneForm.getTitle())
                .startDate(milestoneForm.getStartDate())
                .endDate(milestoneForm.getEndDate())
                .project(Project.builder().id(milestoneForm.getProjectId()).build())
                .createdAt(LocalDateTime.now())
                .status(milestoneForm.getStatus())
                .creator(userService.getCurrentLoginUser()).build());
    }

    @Override
    public void updateProjectMilestone(UpdateMilestoneForm milestoneForm) {
        Milestone milestone = getProjectMilestoneByID(milestoneForm.getId());
        milestone.setTitle(milestoneForm.getTitle());
        milestone.setStartDate(milestoneForm.getStartDate());
        milestone.setEndDate(milestoneForm.getEndDate());
        milestone.setModifiedAt(LocalDateTime.now());
        milestone.setStatus(milestoneForm.getStatus());
        milestone.setModifier(userService.getCurrentLoginUser());
        milestoneRepository.save(milestone);
    }

    @Override
    public void deleteProjectMilestone(Long id) {
        milestoneRepository.deleteById(id);
    }

    @Override
    public boolean isProjectMilestoneExistsByID(Long id) {
        return milestoneRepository.existsById(id);
    }

    @Override
    public boolean isProjectMilestoneExistsByTitle(String title, Long projectId) {
        return milestoneRepository.existsByTitle(title, projectId);
    }
}
