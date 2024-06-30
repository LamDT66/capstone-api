package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fpt.config.internationalization.MessageProperty;
import com.fpt.entity.Issue;
import com.fpt.entity.Milestone;
import com.fpt.entity.ProjectSetting;
import com.fpt.entity.User;
import com.fpt.enums.UserRole;
import com.fpt.form.issue.AddIssueForm;
import com.fpt.form.issue.FilterIssueForm;
import com.fpt.form.issue.UpdateIssueForm;
import com.fpt.repository.IssueRepository;
import com.fpt.service.EmailService;
import com.fpt.service.IssueService;
import com.fpt.service.ProjectService;
import com.fpt.service.UserService;
import com.fpt.specification.issue.IssueSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

	@Autowired
	private IssueRepository repository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MessageProperty messageProperty;

	@Override
	public Page<Issue> getAllIssues(Pageable pageable, String search, FilterIssueForm filterForm) {
		User currentUser = userService.getCurrentLoginUser();
		String currentRole = currentUser.getRole().getSettingName();

		 if (currentRole.equals(UserRole.TEACHER.toString())) {
			filterForm.setTeacherId(currentUser.getId());
		} else {
			// student
			filterForm.setStudentId(currentUser.getId());
		}
		Specification<Issue> where = IssueSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	@Override
	public List<Issue> getIssueByProjectId(Long id) {
		return null;
	}

	@Override
	public void createIssue(AddIssueForm form) {
		User creator = userService.getCurrentLoginUser();
		
		Issue entity = Issue.builder()
				.milestone(Milestone.builder().id(form.getMilestoneId()).build())
				.title(form.getTitle())
				.description(form.getDescription())
				.type(ProjectSetting.builder().id(form.getTypeId()).build())
				.process(ProjectSetting.builder().id(form.getProcessId()).build())				
				.assignee(User.builder().id(form.getAssigneeId()).build())
				.assigner(User.builder().id(creator.getId()).build())
				.project(projectService.getProjectByStudent())
				.status(form.getStatus())
				.createdAt(LocalDateTime.now())
				.creator(creator)
				.modifiedAt(LocalDateTime.now())
				.modifier(creator)
				.build();

		Issue issue = repository.save(entity);
		
		// sending mail
		emailService.sendEmail(
				userService.getUserById(form.getAssigneeId()).getEmail(), 
				messageProperty.getMessage("email.send.issue.add.title"), 
				creator.getFullName() + " " + messageProperty.getMessage("email.send.issue.add.body") + "(ID = " + issue.getId() + ") + (Title = " + issue.getTitle() + ") for you");
	}

	@Override
	public void updateIssue(UpdateIssueForm form) {
		Issue entity = getIssueById(form.getId());
		
		entity.setMilestone(Milestone.builder().id(form.getMilestoneId()).build());
		entity.setTitle(form.getTitle());
		entity.setDescription(form.getDescription());
		entity.setType(ProjectSetting.builder().id(form.getTypeId()).build());
		entity.setProcess(ProjectSetting.builder().id(form.getProcessId()).build());
		entity.setAssignee(User.builder().id(form.getAssigneeId()).build());
		entity.setStatus(form.getStatus());
		
		entity.setModifier(userService.getCurrentLoginUser());
		entity.setModifiedAt(LocalDateTime.now());
		
		repository.save(entity);
	}

	@Override
	public void deleteIssue(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Issue> getIssueByAssigneeId(Long id) {
		return repository.getIssueByAssigneeId(id);
	}

	@Override
	public Issue getIssueById(Long id) {
		return repository.findById(id).get();
	}
}
