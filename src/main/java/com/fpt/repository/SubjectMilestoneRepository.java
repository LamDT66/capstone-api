package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpt.entity.SubjectMilestone;

public interface SubjectMilestoneRepository extends JpaRepository<SubjectMilestone, Long> {
    @Query("SELECT sm FROM SubjectMilestone sm " +
            "JOIN Subject s ON sm.subject.id = s.id WHERE sm.subject.id = :subjectId")
    List<SubjectMilestone> findAllBySubjectId(@Param("subjectId") Long subjectId);

    boolean existsByTitle(String title);
}
