package com.fpt.controller;

import java.util.List;
import com.fpt.dto.ProjectSettingDTO;
import com.fpt.enums.ProjectSettingType;
import com.fpt.form.project_setting.AddProjectSettingForm;
import com.fpt.form.project_setting.UpdateProjectSettingForm;
import com.fpt.validation.form.projectsetting.ProjectSettingIDExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fpt.dto.ProjectSettingTypeDTO;
import com.fpt.entity.ProjectSetting;
import com.fpt.service.ProjectSettingService;

@RestController
@RequestMapping(value = "api/v1/project-settings")
@Validated
public class ProjectSettingController extends BaseController {

	@Autowired
	private ProjectSettingService service;

	@GetMapping(value = "/types/{type}")
	public List<ProjectSettingTypeDTO> viewAllBySettingTypeAndStudent(
			@PathVariable(name = "type") ProjectSettingType type) {
		List<ProjectSetting> entity = service.viewAllBySettingTypeAndStudent(type);
		return convertListEntityToListDto(entity, ProjectSettingTypeDTO.class);
	}

	@GetMapping(value = "/typess")
	public Page<ProjectSettingTypeDTO> viewAllType(Pageable pageable) {
		Page<ProjectSetting> entitySettingPage = service.viewAllType(pageable);
		return convertEntityPageToDtoPage(entitySettingPage, pageable, ProjectSettingTypeDTO.class);
	}

	@GetMapping(value = "/processes")
	public Page<ProjectSettingTypeDTO> viewAllProcess(Pageable pageable) {
		Page<ProjectSetting> entitySettingPage = service.viewAllProcess(pageable);
		return convertEntityPageToDtoPage(entitySettingPage, pageable, ProjectSettingTypeDTO.class);
	}

	@GetMapping(value = "/statuses")
	public Page<ProjectSettingTypeDTO> viewAllStatus(Pageable pageable) {
		Page<ProjectSetting> entitySettingPage = service.viewAllStatus(pageable);
		return convertEntityPageToDtoPage(entitySettingPage, pageable, ProjectSettingTypeDTO.class);
	}

	@GetMapping(value = "/project/{id}/types")
	public List<ProjectSettingTypeDTO> getAllTypesByProjectId(@PathVariable(name = "id") Long id){
		List<ProjectSetting> projectSettings = service.getAllTypesByProjectId(id);
		return convertListEntityToListDto(projectSettings, ProjectSettingTypeDTO.class);
	}

	@GetMapping(value = "/project/{id}/processes")
	public List<ProjectSettingTypeDTO> getAllProcessesByProjectId(@PathVariable(name = "id") Long id){
		List<ProjectSetting> projectSettings = service.getAllProcessesByProjectId(id);
		return convertListEntityToListDto(projectSettings, ProjectSettingTypeDTO.class);
	}

	@GetMapping(value = "/project/{id}/statuses")
	public List<ProjectSettingTypeDTO> getAllStatusesByProjectId(@PathVariable(name = "id") Long id){
		List<ProjectSetting> projectSettings = service.getAllStatusesByProjectId(id);
		return convertListEntityToListDto(projectSettings, ProjectSettingTypeDTO.class);
	}

	@GetMapping(value = "/project/{id}")
	public Page<ProjectSettingDTO> getAllSettings(@PathVariable(name = "id") Long id, Pageable pageable) {

		// get entity
		Page<ProjectSetting> entitySettingPage = service.getAllProjectSettings(id, pageable);

		// convert entity to dto
		return convertEntityPageToDtoPage(entitySettingPage, pageable, ProjectSettingDTO.class);
	}

	@GetMapping("/types")
	public List<ProjectSettingType> getAllSettingTypes() {
		return service.getAllProjectSettingTypes();
	}

	@GetMapping(value = "/project/type/exists")
	public Boolean isSettingNameExists(
			Long projectId,
			ProjectSettingType settingType,
			String name) {
		return service.isProjectSettingExistsBySettingName(projectId, settingType, name);
	}

	@PostMapping(value = "/project/{id}")
	public String createSetting(@RequestBody AddProjectSettingForm projectSettingForm, @PathVariable(name = "id") Long id) {
		projectSettingForm.setProjectId(id);
		service.createProjectSetting(projectSettingForm);
		return "Create successfully!";
	}

	@GetMapping(value = "/{id}")
	public ProjectSettingDTO getSettingById(@PathVariable(name = "id") @ProjectSettingIDExists Long id) {
		// get entity
		ProjectSetting entitySetting = service.getProjectSettingByID(id);

		// convert entity to dto
		return convertEntityToDto(entitySetting, ProjectSettingDTO.class);
	}

	@PutMapping(value = "/{id}/project/{projectId}")
	public String updateSetting(@PathVariable(name = "id") @ProjectSettingIDExists Long id,
								@PathVariable(name = "projectId") Long projectId,
								@RequestBody @Valid UpdateProjectSettingForm settingForm) {
		settingForm.setId(id);
		settingForm.setProjectId(projectId);
		service.updateProjectSetting(settingForm);
		return "Update successfully!";
	}

	@DeleteMapping(value = "/{id}")
	public String deleteSetting(@PathVariable(name = "id") @ProjectSettingIDExists Long id) {
		service.deleteProjectSetting(id);
		return "Delete successfully!";
	}

	@GetMapping("/process")
	public List<ProjectSettingDTO> getAllProcesses() {
		List<ProjectSetting> entities = service.getAllProcess();
		return convertListEntityToListDto(entities, ProjectSettingDTO.class);
	}

	@GetMapping("/status")
	public List<ProjectSettingDTO> getAllStatuses() {
		List<ProjectSetting> entities = service.getAllStatus();
		return convertListEntityToListDto(entities, ProjectSettingDTO.class);
	}

	@GetMapping("type")
	public List<ProjectSettingDTO> getAllTypes() {
		List<ProjectSetting> entities = service.getAllTypes();
		return convertListEntityToListDto(entities, ProjectSettingDTO.class);
	}
}
