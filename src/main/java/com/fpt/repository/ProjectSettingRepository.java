package com.fpt.repository;

import com.fpt.enums.ProjectSettingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.entity.ProjectSetting;
import com.fpt.enums.ProjectSettingType;

import java.util.List;

@Repository
public interface ProjectSettingRepository extends JpaRepository<ProjectSetting, Long>, JpaSpecificationExecutor<ProjectSetting> {

    @Query(value = "SELECT 	ps.* "
            + "FROM 	project_setting ps "
            + "JOIN	student_project sp ON sp.project_id = ps.project_id "
            + "WHERE 	sp.student_id = :studentId AND ps.setting_type = :type", nativeQuery = true)
    List<ProjectSetting> findAllBySettingTypeAndStudent(@Param(value = "type") String type, @Param(value = "studentId") Long studentId);

    @Query(value = "SELECT * FROM project_setting WHERE setting_type = 'PROCESS'", nativeQuery = true)
    Page<ProjectSetting> viewAllProcess(Pageable pageable);

    @Query(value = "SELECT * FROM project_setting WHERE setting_type = 'TYPE'", nativeQuery = true)
    Page<ProjectSetting> viewAllType(Pageable pageable);

    @Query(value = "SELECT * FROM project_setting WHERE setting_type = 'STATUS'", nativeQuery = true)
    Page<ProjectSetting> viewAllStatus(Pageable pageable);

    @Query(value = "SELECT p FROM ProjectSetting p WHERE p.settingType = 'TYPE' AND p.project.id = :id")
    List<ProjectSetting> getAllTypesByProjectId(Long id);

    @Query(value = "SELECT p FROM ProjectSetting p WHERE p.settingType = 'PROCESS' AND p.project.id = :id")
    List<ProjectSetting> getAllProcessesByProjectId(Long id);

    @Query(value = "SELECT p FROM ProjectSetting p WHERE p.settingType = 'STATUS' AND p.project.id = :id")
    List<ProjectSetting> getAllStatusesByProjectId(Long id);

    @Query(value = "SELECT p FROM ProjectSetting p WHERE p.project.id = :projectId AND p.settingType = :settingType AND p.settingName = :name")
	List<ProjectSetting> getSettingNameByProjectId(Long projectId, ProjectSettingType settingType, String name);

    List<ProjectSetting> findAllBySettingType(ProjectSettingType settingType);

    @Query(value = "select distinct setting_type from project_setting", nativeQuery = true)
    List<ProjectSettingType> findAllSettingType();

    @Query(value = "select * from project_setting where project_id = :id", nativeQuery = true)
    Page<ProjectSetting> findAllByProjectId(@Param("id") Long id, Pageable pageable);
}
