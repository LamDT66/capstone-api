package com.fpt.service;

import java.util.List;

import com.fpt.enums.ProjectSettingType;
import com.fpt.form.project_setting.AddProjectSettingForm;
import com.fpt.form.project_setting.UpdateProjectSettingForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.ProjectSetting;
import com.fpt.enums.ProjectSettingType;

public interface ProjectSettingService {
	
	List<ProjectSetting> viewAllBySettingTypeAndStudent(ProjectSettingType type);

    Page<ProjectSetting> viewAllType(Pageable pageable);

    Page<ProjectSetting> viewAllProcess(Pageable pageable);

    Page<ProjectSetting> viewAllStatus(Pageable pageable);

    List<ProjectSetting> getAllTypesByProjectId(Long id);

    List<ProjectSetting> getAllProcessesByProjectId(Long id);

    List<ProjectSetting> getAllStatusesByProjectId(Long id);

//    Page<ProjectSetting> getAllProjectSettings(Pageable pageable, String search);

    Page<ProjectSetting> getAllProjectSettings(Long id, Pageable pageable);

    List<ProjectSettingType> getAllProjectSettingTypes();

    List<ProjectSetting> getAllProcess();

    List<ProjectSetting> getAllStatus();

    List<ProjectSetting> getAllTypes();

    ProjectSetting getProjectSettingByID(Long id);

    void createProjectSetting(AddProjectSettingForm projectSettingForm);

    void updateProjectSetting(UpdateProjectSettingForm projectSettingForm);

    void deleteProjectSetting(Long id);
    boolean isProjectSettingExistsByID(Long id);

    boolean isProjectSettingExistsBySettingName(Long projectId, ProjectSettingType settingType, String settingName);
}
