package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query(value="select p.* from project p where p.mentor_id=:id or p.co_mentor_id=:id", nativeQuery = true)
    List<Project> getAssignedProjectsForTeacher(@Param("id") Long id);

    @Query(value="select p.* from project p join project_student ps on p.id = ps.project_id where ps.user_id =:id", nativeQuery = true)
    List<Project> getAssignedProjectsForStudent(@Param("id") Long id);

    boolean existsByProjectCode(String code);

    @Query(value = "SELECT p.* FROM project p JOIN milestone m ON m.project_id = p.id WHERE m.id = :milestoneId", nativeQuery = true)
    Project getProjectByMilestoneId(Long milestoneId);
}
