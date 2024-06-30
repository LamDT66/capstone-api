package com.fpt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.Issue;
import com.fpt.form.issue.AddIssueForm;
import com.fpt.form.issue.FilterIssueForm;
import com.fpt.form.issue.UpdateIssueForm;

public interface IssueService {

	Page<Issue> getAllIssues(Pageable pageable, String search, FilterIssueForm filterForm);

	List<Issue> getIssueByProjectId(Long id);

	void createIssue(AddIssueForm form);

	void updateIssue(UpdateIssueForm issue);

	void deleteIssue(Long id);

	Issue getIssueById(Long id);

	List<Issue> getIssueByAssigneeId(Long id);
}
