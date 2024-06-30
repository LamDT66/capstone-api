package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Setting;
import com.fpt.enums.SettingType;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long>, JpaSpecificationExecutor<Setting> {
	boolean existsBySettingName(String settingName);
	
	boolean existsBySettingTypeAndSettingDisplayOrder(SettingType type, Integer displayOrder);
	
	List<Setting> findAllBySettingTypeOrderBySettingDisplayOrderAsc(SettingType settingType);

    @Query(value = "select distinct setting_type from setting", nativeQuery = true)
    List<SettingType> findAllSettingType();
}
