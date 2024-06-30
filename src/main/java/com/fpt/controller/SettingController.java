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

import com.fpt.dto.SettingDTO;
import com.fpt.dto.SettingTypeDTO;
import com.fpt.entity.Setting;
import com.fpt.enums.SettingType;
import com.fpt.form.setting.AddSettingForm;
import com.fpt.form.setting.UpdateSettingForm;
import com.fpt.service.SettingService;
import com.fpt.validation.form.setting.SettingIDExists;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/settings")
@Validated
public class SettingController extends BaseController {

	@Autowired
	private SettingService service;

	@GetMapping
	public Page<SettingDTO> getAllSettings(
			Pageable pageable, 
			@RequestParam(value = "q", required = false) String search) {
		
		// get entity
		Page<Setting> entitySettingPage = service.getAllSettings(pageable, search);

		// convert entity to dto
		return convertEntityPageToDtoPage(entitySettingPage, pageable, SettingDTO.class);
	}

	@GetMapping("/types")
	public List<SettingType> getAllSettingTypes() {
		return service.getAllSettingTypes();
	}
	
	@GetMapping(value = "/name/exists")
	public Boolean isSettingNameExists(String name) {
		return service.isSettingExistsBySettingName(name);
	}
	
	@GetMapping(value = "/display-order/exists")
	public Boolean isSettingDisplayOrderExists(SettingType type, Integer displayOrder) {
		return service.isSettingExistsBySettingDisplayOrder(type, displayOrder);
	}
	
	@PostMapping
	public String createSetting(@RequestBody @Valid AddSettingForm settingForm) {
		service.createSetting(settingForm);
		return "Create successfully!";
	}
	
	@GetMapping(value = "/{id}")
	public SettingDTO getSettingById(@PathVariable(name = "id") @SettingIDExists Long id) {
		// get entity
		Setting entitySetting = service.getSettingByID(id);

		// convert entity to dto
		return convertEntityToDto(entitySetting, SettingDTO.class);
	}
	
	@PutMapping(value = "/{id}")
	public String updateSetting(@PathVariable(name = "id") @SettingIDExists Long id,
			@RequestBody @Valid UpdateSettingForm settingForm) {
		settingForm.setId(id);
		service.updateSetting(settingForm);
		return "Update successfully!";
	}
	
	@DeleteMapping(value = "/{id}")
	public String deleteSetting(@PathVariable(name = "id") @SettingIDExists Long id) {
		service.deleteSetting(id);
		return "Delete successfully!";
	}
	
	@GetMapping("/faculties")
	public List<SettingDTO> getAllFaculties() {
		List<Setting> entities = service.getAllFaculties();
		return convertListEntityToListDto(entities, SettingDTO.class);
	}
	
	@GetMapping("/semesters")
	public List<SettingDTO> getAllSemesters() {
		List<Setting> entities = service.getAllSemesters();
		return convertListEntityToListDto(entities, SettingDTO.class);
	}
	
	@GetMapping(value = "/userRoles")
	public List<SettingTypeDTO> getAllUserRoles() {
		List<Setting> entityPage = service.getAllUserRoles();
		return convertListEntityToListDto(entityPage, SettingTypeDTO.class);
	}
}
