package com.fpt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.Project;
import com.fpt.form.project.AddProjectForm;
import com.fpt.form.project.FilterProjectForm;
import com.fpt.form.project.UpdateProjectForm;

public interface ProjectService {

	Page<Project> getAllProjects(Pageable pageable, String search, FilterProjectForm filterForm);

	Project getProjectByStudent();
	
	List<Project> getAllProjectsForSelection();
	
	Project findById(Long id);

	boolean isProjectExistsByCode(String code);

	void addProject(AddProjectForm addProjectForm);

	void updateProjectById(UpdateProjectForm projectForm);

	void importProjectByExcelFile(List<AddProjectForm> form);

	void updateStatus(Long id);

    List<Project> getAssignedProjects();
}
