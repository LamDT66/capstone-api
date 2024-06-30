package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fpt.entity.Project;
import com.fpt.entity.ProjectSetting;
import com.fpt.enums.ProjectSettingType;
import com.fpt.form.project_setting.AddProjectSettingForm;
import com.fpt.form.project_setting.UpdateProjectSettingForm;
import com.fpt.repository.ProjectRepository;
import com.fpt.repository.ProjectSettingRepository;
import com.fpt.service.ProjectSettingService;
import com.fpt.service.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectSettingServiceImpl implements ProjectSettingService {

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectSettingRepository repository;

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public List<ProjectSetting> viewAllBySettingTypeAndStudent(ProjectSettingType type) {
		return repository.findAllBySettingTypeAndStudent(type.name(), userService.getCurrentLoginUser().getId());
	}

	@Override
	public Page<ProjectSetting> viewAllType(Pageable pageable) {
		return repository.viewAllType(pageable);
	}

	@Override
	public Page<ProjectSetting> viewAllProcess(Pageable pageable) {
		return repository.viewAllProcess(pageable);
	}

	@Override
	public Page<ProjectSetting> viewAllStatus(Pageable pageable) {
		return repository.viewAllStatus(pageable);
	}

	@Override
	public List<ProjectSetting> getAllTypesByProjectId(Long id) {
		return repository.getAllTypesByProjectId(id);
	}

	@Override
	public List<ProjectSetting> getAllProcessesByProjectId(Long id) {
		return repository.getAllProcessesByProjectId(id);
	}

	@Override
	public List<ProjectSetting> getAllStatusesByProjectId(Long id) {
		return repository.getAllStatusesByProjectId(id);
	}

//	@Override
//	public Page<ProjectSetting> getAllProjectSettings(Pageable pageable, String search){
//		Specification<ProjectSetting> where = ProjectSettingSpecification.buildWhere(search);
//		return repository.findAll(where, pageable);
//	}

	@Override
	public Page<ProjectSetting> getAllProjectSettings(Long id, Pageable pageable) {
		return repository.findAllByProjectId(id, pageable);
	}

	@Override
	public List<ProjectSettingType> getAllProjectSettingTypes() {
		return repository.findAllSettingType();
	}

	@Override
	public List<ProjectSetting> getAllProcess() {
		return repository.findAllBySettingType(ProjectSettingType.PROCESS);
	}

	@Override
	public List<ProjectSetting> getAllStatus() {
		return repository.findAllBySettingType(ProjectSettingType.STATUS);
	}

	@Override
	public List<ProjectSetting> getAllTypes() {
		return repository.findAllBySettingType(ProjectSettingType.TYPE);
	}

	@Override
	public ProjectSetting getProjectSettingByID(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public void createProjectSetting(AddProjectSettingForm projectSettingForm) {
		ProjectSetting projectSetting = repository.save(ProjectSetting.builder()
				.settingType(projectSettingForm.getSettingType())
				.settingName(projectSettingForm.getSettingName())
				.project(Project.builder().id(projectSettingForm.getProjectId()).build())
				.createdAt(LocalDateTime.now())
				.creator(userService.getCurrentLoginUser()).build());
	}

	@Override
	public void updateProjectSetting(UpdateProjectSettingForm projectSettingForm) {
		ProjectSetting entity = getProjectSettingByID(projectSettingForm.getId());
		entity.setSettingType(projectSettingForm.getSettingType());
		entity.setSettingName(projectSettingForm.getSettingName());
		entity.setProject(projectRepository.findById(projectSettingForm.getProjectId()).get());
		entity.setModifiedAt(java.time.LocalDateTime.now());
		entity.setModifier(userService.getCurrentLoginUser());
		repository.save(entity);
	}

	@Override
	public void deleteProjectSetting(Long id) {
		repository.deleteById(id);
	}

	@Override
	public boolean isProjectSettingExistsByID(Long id) {
		return repository.existsById(id);
	}

	@Override
	public boolean isProjectSettingExistsBySettingName(Long projectId, ProjectSettingType settingType, String settingName) {
		return !repository.getSettingNameByProjectId(projectId, settingType, settingName).isEmpty();
	}
}
