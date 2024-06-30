package com.fpt.controller;

import com.fpt.dto.MilestoneDTO;
import com.fpt.entity.Milestone;
import com.fpt.form.milestone_project.AddMilestoneForm;
import com.fpt.form.milestone_project.UpdateMilestoneForm;
import com.fpt.service.ProjectMilestoneService;
import com.fpt.validation.form.projectmilestone.ProjectMilestoneIDExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/project-milestones")
@Validated
public class ProjectMilestoneController extends BaseController {

    @Autowired
    private ProjectMilestoneService service;

    @GetMapping(value = "/project/{id}")
    public Page<MilestoneDTO> getAllMilestonesByProjectId(Pageable pageable,
    		@PathVariable(name = "id") Long id,
            @RequestParam(value = "q", required = false) String search) {
        Page<Milestone> entityPage = service.getAllProjectMilestones(id, pageable, search);
        return convertEntityPageToDtoPage(entityPage, pageable, MilestoneDTO.class);
    }

    @GetMapping(value = "/title/exists")
    public Boolean isMilestoneTitleExists(String title, Long projectId) {
        return service.isProjectMilestoneExistsByTitle(title, projectId);
    }

    @PostMapping(value = "/project/{id}")
    public String createMilestone(@PathVariable(name = "id") Long id, @RequestBody AddMilestoneForm milestoneForm) {
        milestoneForm.setProjectId(id);
        service.createProjectMilestone(milestoneForm);
        return "Create milestone successfully";
    }

    @GetMapping(value = "/{id}")
    public MilestoneDTO getMilestoneById(@PathVariable(name = "id") @ProjectMilestoneIDExists Long id) {
        Milestone milestone = service.getProjectMilestoneByID(id);
        return convertEntityToDto(milestone, MilestoneDTO.class);
    }

    @PutMapping(value = "/{id}")
    public String updateMilestone(@PathVariable(name = "id") @ProjectMilestoneIDExists Long id,
                                  @RequestBody @Valid UpdateMilestoneForm milestoneForm) {
        milestoneForm.setId(id);
        service.updateProjectMilestone(milestoneForm);
        return "Update milestone successfully";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteMilestone(@PathVariable(name = "id") @ProjectMilestoneIDExists Long id) {
        service.deleteProjectMilestone(id);
        return "Delete milestone successfully";
    }
}
