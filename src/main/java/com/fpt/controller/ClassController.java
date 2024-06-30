package com.fpt.controller;

import com.fpt.dto.ClassForSelectionDTO;
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

import com.fpt.dto.ClassDTO;
import com.fpt.entity.Class;
import com.fpt.form.clazz.AddClassForm;
import com.fpt.form.clazz.FilterClassForm;
import com.fpt.form.clazz.UpdateClassForm;
import com.fpt.service.ClassService;
import com.fpt.validation.form.clazz.ClassIDExists;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
@Validated
public class ClassController extends BaseController {

	@Autowired
	private ClassService service;

	@GetMapping
	public Page<ClassDTO> getAllClasses(Pageable pageable,
			@RequestParam(value = "q", required = false) String search,
			FilterClassForm filterForm) {
		Page<Class> entityPage = service.getAllClasses(pageable, search, filterForm);
		return convertEntityPageToDtoPage(entityPage, pageable, ClassDTO.class);
	}
	
	@GetMapping("/selection")
	public List<ClassForSelectionDTO> getAllClassesForSelection() {
		List<Class> entities = service.getAllClassesForSelection();
		return convertListEntityToListDto(entities, ClassForSelectionDTO.class);
	}
	
	@GetMapping(value = "/managers")
	public List<ClassForSelectionDTO> getClassByManagerId() {
		List<Class> entities = service.getAllClassesOfManager();
		return convertListEntityToListDto(entities, ClassForSelectionDTO.class);
	}

	@GetMapping(value = "/{id}")
	public ClassDTO getClassById(@PathVariable(name = "id") @ClassIDExists Long id) {
		Class entity = service.getClassByID(id);
		return modelMapper.map(entity, ClassDTO.class);
	}
	
	@GetMapping(value = "/name/exists")
	public Boolean isNameExists(String name) {
		return service.isClassExistsByName(name);
	}

	@PostMapping
	public String createClass(@RequestBody @Valid AddClassForm form) {
		service.createClass(form);
		return "Create successfully!";
	}
	
	@PutMapping(value = "/{id}")
	public String updateClass(
			@PathVariable(name = "id") @ClassIDExists Long id,
			@RequestBody @Valid UpdateClassForm form) {
		form.setId(id);
		service.updateClass(form);
		return "Update successfully!";
	}
	
	@DeleteMapping(value = "/{id}")
	public String deleteClass(@PathVariable(name = "id") @ClassIDExists Long id) {
		service.deleteClass(id);
		return "Delete successfully!";
	}
}
