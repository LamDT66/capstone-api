package com.fpt.repository;

import com.fpt.entity.Issue;
import com.fpt.form.issue.AddIssueForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

//    @Query(value = "SELECT i FROM Issue i WHERE i.milestone.project.id = :id")
//    List<Issue> getIssueByProjectId(@Param("id") Long id);

    @Query(value = "SELECT * FROM Issue i WHERE i.assignee_id = :id", nativeQuery = true)
    List<Issue> getIssueByAssigneeId(@Param("id") Long id);

    @Query(value ="select title, description, type_id, process_id, status_id, assignee_id, project_id from Issue", nativeQuery = true)
    List<AddIssueForm> findAllIssues();
}

