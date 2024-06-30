package com.fpt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.Setting;
import com.fpt.enums.SettingType;
import com.fpt.form.setting.AddSettingForm;
import com.fpt.form.setting.UpdateSettingForm;

public interface SettingService {
	Page<Setting> getAllSettings(Pageable pageable, String search);

	List<SettingType> getAllSettingTypes();

	List<Setting> getAllFaculties();
	
	List<Setting> getAllSemesters();

	Setting getSettingById(Long id);

	List<Setting> getAllUserRoles();

	Setting getSettingByID(Long id);

	void createSetting(AddSettingForm settingForm);

	void updateSetting(UpdateSettingForm settingForm);

	void deleteSetting(Long id);

	boolean isSettingExistsByID(Long id);

	boolean isSettingExistsBySettingName(String settingName);
	
	boolean isSettingExistsBySettingDisplayOrder(SettingType type, Integer displayOrder);
}
