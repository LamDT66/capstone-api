package com.fpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.dto.ListStudentEmailDTO;
import com.fpt.entity.StudentProject;
import com.fpt.service.ProjectStudentService;

@RestController
@RequestMapping(value = "api/v1/project-members")
public class ProjectStudentController extends BaseController {

	@Autowired
	private ProjectStudentService service;

	@GetMapping(value = "/project/{id}")
	public List<ListStudentEmailDTO> getMembersByProjectId(@PathVariable(name = "id") Long id) {
		List<StudentProject> listEntityProjectStudent = service.getMembersByProjectId(id);
		return convertListEntityToListDto(listEntityProjectStudent, ListStudentEmailDTO.class);
	}
}
