package com.fpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.dto.IssueDTO;
import com.fpt.dto.IssueDetailDTO;
import com.fpt.entity.Issue;
import com.fpt.form.issue.AddIssueForm;
import com.fpt.form.issue.FilterIssueForm;
import com.fpt.form.issue.UpdateIssueForm;
import com.fpt.service.IssueService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/v1/issues")
@AllArgsConstructor
public class IssueController extends BaseController {

	@Autowired
	private IssueService service;

	@GetMapping
	public Page<IssueDTO> getAllIssues(Pageable pageable,
			@RequestParam(value = "q", required = false) String search, 
			FilterIssueForm filterForm) {
		Page<Issue> entityPage = service.getAllIssues(pageable, search, filterForm);
		return convertEntityPageToDtoPage(entityPage, pageable, IssueDTO.class);
	}

	@GetMapping(value = "/project/{id}")
	public List<IssueDetailDTO> getIssueByProjectId(@PathVariable(name = "id") Long id) {
		// get entity
		List<Issue> issues = service.getIssueByProjectId(id);

		// convert entity to dto
		return convertListEntityToListDto(issues, IssueDetailDTO.class);
	}

	@GetMapping(value = "/{id}")
	public IssueDetailDTO getIssueById(@PathVariable(name = "id") Long id) {
		// get entity
		Issue entity = service.getIssueById(id);

		// convert entity to dto
		return convertEntityToDto(entity, IssueDetailDTO.class);
	}

	@PostMapping
	public String createIssue(@RequestBody @Valid AddIssueForm issueForm) {
		// create entity
		service.createIssue(issueForm);
		return "Create successfully!";
	}

	@PutMapping(value = "/{id}")
	public String updateIssue(@PathVariable(name = "id") Long id,
							  @RequestBody @Valid UpdateIssueForm issueForm) {
		issueForm.setId(id);

		// update entity
		service.updateIssue(issueForm);
		return "Update successfully!";
	}

	@DeleteMapping(value = "/{id}")
	public String deleteIssue(@PathVariable(name = "id") Long id) {
		// delete entity
		service.deleteIssue(id);
		return "Delete successfully!";
	}

	@GetMapping(value = "/assignee/{id}")
	public List<IssueDTO> getIssueByAssigneeId(@PathVariable(name = "id") Long id) {
		// get entity
		List<Issue> entityIssueList = service.getIssueByAssigneeId(id);

		// convert entity to dto
		List<IssueDTO> dtoList = convertListEntityToListDto(entityIssueList, IssueDTO.class);

		return dtoList;
	}
}
