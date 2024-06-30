package com.fpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.dto.Response;
import com.fpt.dto.SubjectMilestoneDTO;
import com.fpt.entity.SubjectMilestone;
import com.fpt.form.subjectmilestone.AddSubjectMilestoneForm;
import com.fpt.form.subjectmilestone.UpdateSubjectMilestoneForm;
import com.fpt.service.SubjectMilestoneService;
import com.fpt.validation.form.subjectmilestone.SubjectMilestoneIDExists;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/v1/subject-milestones")
@Validated
public class SubjectMilestoneController extends BaseController {

	@Autowired
	private SubjectMilestoneService subjectMilestoneService;

	@GetMapping(value = "/{subjectId}")
	public List<SubjectMilestoneDTO> getAllSubjectMilestone(@PathVariable(name = "subjectId") Long subjectId) {
		// get entity
		List<SubjectMilestone> entityPage = subjectMilestoneService.findAllSubjectMilestone(subjectId);

		// Convert entity to dto
		return convertListEntityToListDto(entityPage, SubjectMilestoneDTO.class);
	}

	@PostMapping(value = "/{subjectId}")
	public ResponseEntity<Response> createSubjectMilestone(@PathVariable(name = "subjectId") Long subjectId,
			@RequestBody @Valid AddSubjectMilestoneForm subjectMilestoneForm) {
		subjectMilestoneService.addSubjectMilestone(subjectMilestoneForm, subjectId);
		Response response = new Response("Create successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}/subject/{subjectId}")
	public ResponseEntity<Response> updateSubjectMilestone(@PathVariable(name = "id") Long id,
			@PathVariable(name = "subjectId") Long subjectId,
			@RequestBody @Valid UpdateSubjectMilestoneForm updateSubjectMilestoneForm) {
		subjectMilestoneService.updateSubjectMilestone(updateSubjectMilestoneForm, id, subjectId);
		Response response = new Response("Update successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public String deleteSubjectMilestoneById(@PathVariable(name = "id") @SubjectMilestoneIDExists Long id) {
		subjectMilestoneService.deleteSubject(id);
		return "Delete successfully!";
	}
}
