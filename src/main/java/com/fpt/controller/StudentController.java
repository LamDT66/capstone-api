package com.fpt.controller;

import java.util.List;

import com.fpt.dto.StudentProjectDTO;
import com.fpt.entity.StudentProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.dto.StudentDTO;
import com.fpt.dto.StudentForSelectionDTO;
import com.fpt.entity.StudentClass;
import com.fpt.entity.User;
import com.fpt.form.student.AddStudentForm;
import com.fpt.form.student.ImportStudentForm;
import com.fpt.service.StudentService;
import com.fpt.validation.form.clazz.ClassIDExists;
import com.fpt.validation.form.user.UserIDExists;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/students")
@Validated
public class StudentController extends BaseController {

	@Autowired
	private StudentService service;

	@GetMapping(value = "/classes/{classId}")
	public Page<StudentDTO> getAllStudentsByClass(Pageable pageable,
			@PathVariable(name = "classId") @ClassIDExists Long classId,
			@RequestParam(value = "q", required = false) String search) {

		// get entity
		Page<StudentClass> entityPage = service.getAllStudentsByClass(pageable, classId, search);

		// convert entity to dto
		return convertEntityPageToDtoPage(entityPage, pageable, StudentDTO.class);
	}

	@GetMapping(value = "/projects/{projectId}")
	public List<StudentProjectDTO> getAllStudentsByProject(@PathVariable(name = "projectId") Long projectId){
		List<StudentProject> studentProjectList = service.getAllStudentsByProject(projectId);
		return convertListEntityToListDto(studentProjectList, StudentProjectDTO.class);
	}

	@GetMapping(value = "/classes/{classId}/noProject")
	public List<StudentForSelectionDTO> getAllStudentsByNoProjectInClass(@PathVariable(name = "classId") Long classId){
		List<User> studentProjectList = service.getAllStudentsByNoProject(classId);
		return convertListEntityToListDto(studentProjectList, StudentForSelectionDTO.class);
	}
	
	@GetMapping(value = "/noClasses")
	public List<StudentForSelectionDTO> getAllStudentsByNoClass() {

		// get entity
		List<User> entityPage = service.getAllStudentsByNoClass();

		// convert entity to dto
		return convertListEntityToListDto(entityPage, StudentForSelectionDTO.class);
	}
	
	@GetMapping("/project")
	public List<StudentForSelectionDTO> getAllStudentsInProject() {
		return convertListEntityToListDto(service.getAllStudentsInProject(), StudentForSelectionDTO.class);
	}

	@PostMapping(value = "/classes/{classId}")
	public void insertStudent(
			@PathVariable(name = "classId") @ClassIDExists Long classId,
			@RequestBody @Valid AddStudentForm studentForm) {
		studentForm.setClassId(classId);
		service.insertStudent(studentForm);
	}
	
	@PostMapping(value = "/classes/{classId}/import")
	public void importStudent(
			@PathVariable(name = "classId") @ClassIDExists Long classId,
			@RequestBody @Valid ImportStudentForm studentForm) {
		studentForm.setClassId(classId);
		service.importStudent(studentForm);
	}
	
	@DeleteMapping(value = "/classes/{studentId}")
	public void removeStudent(@PathVariable(name = "studentId") @UserIDExists Long studentId) {
		service.removeStudent(studentId);
	}
}
