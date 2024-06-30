package com.fpt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report>{

    @Query(value="SELECT r.created_at, r.creator_id, r.id, r.milestone_id, r.modified_at, r.modifier_id, r.project_id, r.submit_time, r.title, r.file_url FROM report r JOIN milestone m ON m.id = r.milestone_id JOIN student_project ps ON ps.project_id = r.project_id JOIN `user` u ON u.id = ps.student_id WHERE u.id = :id ORDER BY submit_time DESC", nativeQuery = true)
    Page<Report> getReportsByStudent(@Param("id") Long id, Pageable pageable);

    @Query(value = "SELECT r.created_at, r.creator_id, r.id, r.milestone_id, r.modified_at, r.modifier_id, r.project_id, r.submit_time, r.title, r.file_url FROM report r JOIN milestone m ON m.id = r.milestone_id JOIN project p ON p.id = r.project_id WHERE p.mentor_id = :id or p.co_mentor_id = :id ORDER BY submit_time DESC", nativeQuery = true)
    Page<Report> getReportsByTeacher(@Param("id") Long id, Pageable pageable);
}
