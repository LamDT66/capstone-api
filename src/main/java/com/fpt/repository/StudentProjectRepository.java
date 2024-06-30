package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.entity.StudentProject;

import java.util.List;

@Repository
public interface StudentProjectRepository extends JpaRepository<StudentProject, Long> {

    @Query(value="SELECT sp FROM StudentProject sp WHERE sp.project.id = :id")
    List<StudentProject> getStudentByProjectId(@Param("id") Long id);
//
//    @Query(value="SELECT ps FROM ProjectStudent ps where ps.student.id = :id")
//    List<StudentProject> getProjectByStudentId(@Param("id") Long id);
//
//    @Modifying
//    @Query(value = "DELETE FROM ProjectStudent ps WHERE ps.project.id = :id")
//    void deleteByProjectId(@Param("id") Long id);
}
