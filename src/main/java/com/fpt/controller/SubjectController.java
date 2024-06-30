package com.fpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.dto.SubjectDTO;
import com.fpt.dto.SubjectForSelectDTO;
import com.fpt.entity.Subject;
import com.fpt.form.subject.AddSubjectForm;
import com.fpt.form.subject.FilterSubjectForm;
import com.fpt.form.subject.UpdateSubjectForm;
import com.fpt.service.SubjectService;
import com.fpt.validation.form.subject.SubjectIDExists;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/subjects")
@Validated
public class SubjectController extends BaseController {

	@Autowired
	private SubjectService service;

	@GetMapping
	public Page<SubjectDTO> getAllSubject(Pageable pageable, 
			@RequestParam(value = "q", required = false) String search,
			FilterSubjectForm filterForm) {

		// get entity
		Page<Subject> entityPage = service.getAllSubject(pageable, search, filterForm);

		// Convert entity to dto
		return convertEntityPageToDtoPage(entityPage, pageable, SubjectDTO.class);
	}
	
	@GetMapping("/selection")
	public List<SubjectForSelectDTO> getAllSubjectForSelection() {

		// get entity
		List<Subject> listEntities = service.getAllSubjectForSelection();

		// Convert entity to dto
		return convertListEntityToListDto(listEntities, SubjectForSelectDTO.class);
	}
	
	@GetMapping(value = "/{id}")
	public SubjectDTO getSubjectByID(@PathVariable(name = "id") @SubjectIDExists Long id) {
		// get entity
		Subject subject = service.getSubjectByID(id);
		// Convert entity to dto
		return convertEntityToDto(subject, SubjectDTO.class);
	}
	
	@GetMapping(value = "/name/exists")
	public boolean isNameExists(String name) {
		return service.isSubjectExistsByName(name);
	}

	@PostMapping
	public String createSubject(@RequestBody @Valid AddSubjectForm subjectForm) {
		service.createSubject(subjectForm);
		return "Create successfully!";
	}

	@PutMapping("/{id}")
	public String updateSubject(@PathVariable(name = "id") Long id, @RequestBody @Valid UpdateSubjectForm form) {
		form.setId(id);
		service.updateSubject(form);
		return "Update successfully!";
	}

	@DeleteMapping(value = "/{id}")
	public String deleteSubjectById(@PathVariable(name = "id") @SubjectIDExists Long id) {
		service.deleteSubject(id);
		return "Delete successfully!";
	}
}
