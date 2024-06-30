package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fpt.entity.Setting;
import com.fpt.entity.User;
import com.fpt.enums.SettingType;
import com.fpt.form.setting.AddSettingForm;
import com.fpt.form.setting.UpdateSettingForm;
import com.fpt.repository.SettingRepository;
import com.fpt.service.SettingService;
import com.fpt.service.UserService;
import com.fpt.specification.setting.SettingSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SettingServiceImpl implements SettingService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SettingRepository repository;

	@Override
	public Page<Setting> getAllSettings(Pageable pageable, String search) {
		Specification<Setting> where = SettingSpecification.buildWhere(search);
		return repository.findAll(where, pageable);
	}
	
	@Override
	public List<SettingType> getAllSettingTypes() {
		return repository.findAllSettingType();
	}
	
	@Override
	public void createSetting(AddSettingForm form) {
		User creator = userService.getCurrentLoginUser();
		
		Setting entity = Setting.builder()
				.settingName(form.getSettingName())
				.settingType(form.getSettingType())
				.settingDisplayOrder(form.getSettingDisplayOrder())
				.createdAt(LocalDateTime.now())
				.creator(creator)
				.modifiedAt(LocalDateTime.now())
				.modifier(creator)
				.build();
		
		repository.save(entity);		
	}

	@Override
	public Setting getSettingByID(Long id) {
		return repository.findById(id).get();
	}
	
	@Override
	public void updateSetting(UpdateSettingForm form) {
		Setting entity = getSettingByID(form.getId());
		entity.setSettingType(form.getSettingType());
		entity.setSettingName(form.getSettingName());
		entity.setSettingDisplayOrder(form.getSettingDisplayOrder());
		entity.setModifiedAt(LocalDateTime.now());
		entity.setModifier(userService.getCurrentLoginUser());
		repository.save(entity);
	}

	@Override
	public List<Setting> getAllFaculties() {
		return repository.findAllBySettingTypeOrderBySettingDisplayOrderAsc(SettingType.FACULTY);
	}
	
	@Override
	public List<Setting> getAllSemesters() {
		return repository.findAllBySettingTypeOrderBySettingDisplayOrderAsc(SettingType.SEMESTER);
	}

	@Override
	public Setting getSettingById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Setting> getAllUserRoles() {
		return repository.findAllBySettingTypeOrderBySettingDisplayOrderAsc(SettingType.ROLE);
	}

	@Override
	public void deleteSetting(Long id) {
		repository.deleteById(id);
	}

	@Override
	public boolean isSettingExistsByID(Long id) {
		return repository.existsById(id);
	}

	@Override
	public boolean isSettingExistsBySettingName(String settingName) {
		return repository.existsBySettingName(settingName);
	}
	
	@Override
	public boolean isSettingExistsBySettingDisplayOrder(SettingType type, Integer displayOrder) {
		return repository.existsBySettingTypeAndSettingDisplayOrder(type, displayOrder);
	}
}
