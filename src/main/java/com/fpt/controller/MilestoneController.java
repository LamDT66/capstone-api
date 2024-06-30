package com.fpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.dto.MilestoneDTO;
import com.fpt.dto.MilestoneForSelectionDTO;
import com.fpt.dto.Response;
import com.fpt.entity.Milestone;
import com.fpt.form.milestone_project.AddMilestoneForm;
import com.fpt.form.milestone_project.UpdateMilestoneForm;
import com.fpt.service.MilestoneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/milestones")
public class MilestoneController extends BaseController {

	@Autowired
	private MilestoneService service;

	@GetMapping(value = "/student")
	public List<MilestoneForSelectionDTO> getMilestoneByStudent() {
		List<Milestone> entities = service.getMilestoneByStudent();
		return convertListEntityToListDto(entities, MilestoneForSelectionDTO.class);
	}
	
	@GetMapping(value = "/class/{id}")
	public List<MilestoneDTO> getMilestoneByClassId(@PathVariable(name = "id") Long id) {
		List<Milestone> listEntityMilestone = service.getMilestoneByClassIdOrderByStartDate(id);
		return convertListEntityToListDto(listEntityMilestone, MilestoneDTO.class);
	}

	@GetMapping(value = "/project/{id}")
	public List<MilestoneDTO> getMilestoneByProjectId(@PathVariable(name = "id") Long id) {
		List<Milestone> listEntityMilestone = service.getMilestoneByProjectIdOrderByStartDate(id);
		return convertListEntityToListDto(listEntityMilestone, MilestoneDTO.class);
	}

	@GetMapping(value = "/project/title/exists")
	public Boolean isMilestoneTitleExists(String name) {
		return service.isMilestoneExistsByTitle(name);
	}

	@GetMapping(value = "/{id}")
	public MilestoneDTO getMilestoneById(@PathVariable(name = "id") Long id) {
		Milestone entityMilestone = service.getMilestoneById(id);
		return modelMapper.map(entityMilestone, MilestoneDTO.class);
	}

	@PostMapping("/project")
	public ResponseEntity<Response> createMilestone(@RequestBody @Valid AddMilestoneForm milestoneForm) {
		Milestone milestone = convertEntityToDto(milestoneForm, Milestone.class);
		service.createMilestone(milestone);
		Response response = new Response("Create successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/project/{id}")
	public ResponseEntity<Response> updateMilestone(@PathVariable(name = "id") Long id,
			@RequestBody @Valid UpdateMilestoneForm milestoneForm) {
		milestoneForm.setId(id);
		Milestone milestone = convertEntityToDto(milestoneForm, Milestone.class);
		service.updateMilestone(milestone);
		Response response = new Response("Update successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(value = "/project/{id}")
	public String deleteMilestone(@PathVariable(name = "id") Long id) {
		service.deleteMilestone(id);
		return "Delete successfully!";
	}
}
