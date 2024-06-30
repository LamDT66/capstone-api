package com.fpt.service.impl;

import com.fpt.entity.Setting;
import com.fpt.enums.SettingType;
import com.fpt.form.setting.AddSettingForm;
import com.fpt.form.setting.UpdateSettingForm;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SettingServiceImplTest {

    @Autowired
    private SettingServiceImpl settingService;

    @Test
    void createSetting_RequiredInfo_Test() {

        // Given
        AddSettingForm form = new AddSettingForm();
        form.setSettingType(SettingType.SEMESTER);
        form.setSettingName("SUMMER2023");
        form.setSettingDisplayOrder(1);

        assertDoesNotThrow(() -> {
            settingService.createSetting(form);
        });
        System.out.println("Create setting successfully");
    }

    @Test
    void getAllSettingListSucess_Test(){
        Page<Setting> settings = settingService.getAllSettings(Pageable.unpaged(), "");
        assertEquals(13, settings.getTotalElements());
        System.out.println("Get all setting list successfully");
    }

    @Test
    void getAllSettingsBySearchSuccess_Test() {
        Page<Setting> settings = settingService.getAllSettings(Pageable.unpaged(), "ADMIN");
        assertEquals(1, settings.getTotalElements());
        System.out.println("Get all setting list by search successfully");
    }

    @Test
    void getAllSettingsListFailed_Test(){
        Page<Setting> settings = settingService.getAllSettings(Pageable.unpaged(), "");
        assertNotEquals(0, settings.getTotalElements());
        System.out.println("Get all setting list failed");
    }

    @Test
    void updateSetting_RequiredInfo_Test() {
        // Given
        UpdateSettingForm form = new UpdateSettingForm();
        form.setId(25L);
        form.setSettingType(SettingType.SEMESTER);
        form.setSettingName("SUMMER2023");
        form.setSettingDisplayOrder(2);

        assertDoesNotThrow(() -> {
            settingService.updateSetting(form);
        });
        System.out.println("Update setting successfully");
    }

    @Test
    void deleteSettingByIdSuccess_Test(){
        // Given
        Long id = 26L;
        assertDoesNotThrow(() -> {
            settingService.deleteSetting(id);
        });
        System.out.println("Delete setting successfully");
    }

    @Test
    void findSettingByIdSuccess_Test(){
        // Given
        Long id = 10L;
        Setting actualSetting = settingService.getSettingById(id);
        assertEquals("JAPANESE LANGUAGE", actualSetting.getSettingName());
        System.out.println("Find setting by id successfully");
    }

    @Test
    void createSetting_DuplicateSettingName_Test(){
        // Given
        AddSettingForm form = new AddSettingForm();
        form.setSettingType(SettingType.SEMESTER);
        form.setSettingName("SUMMER2023");
        form.setSettingDisplayOrder(2);

        assertThrows(PersistenceException.class,
                () -> settingService.createSetting(form));
        System.out.println("Create setting failed");
    }

    @Test
    void createSetting_NullSettingName_Test(){
        // Given
        AddSettingForm form = new AddSettingForm();
        form.setSettingType(SettingType.SEMESTER);
        form.setSettingName("");
        form.setSettingDisplayOrder(3);

        assertThrows(PersistenceException.class,
                () -> settingService.createSetting(form));
        System.out.println("Create setting failed");
    }

    @Test
    void createSetting_NullSettingType_Test(){
        // Given
        AddSettingForm form = new AddSettingForm();
        form.setSettingType(null);
        form.setSettingName("SUMMER2024");
        form.setSettingDisplayOrder(4);

        assertThrows(PersistenceException.class,
                () -> settingService.createSetting(form));
        System.out.println("Create setting failed");
    }

    @Test
    void createSetting_NegativeSettingDisplayOrder_Test(){
        // Given
        AddSettingForm form = new AddSettingForm();
        form.setSettingType(SettingType.SEMESTER);
        form.setSettingName("SUMMER2025");
        form.setSettingDisplayOrder(-1);

        assertThrows(PersistenceException.class,
                () -> settingService.createSetting(form));
        System.out.println("Create setting failed");
    }

    @Test
    void updateSetting_NegativeSettingDisplayOrder_Test(){
        // Given
        UpdateSettingForm form = new UpdateSettingForm();
        form.setId(25L);
        form.setSettingType(SettingType.SEMESTER);
        form.setSettingName("SUMMER2025");
        form.setSettingDisplayOrder(-1);

        assertThrows(PersistenceException.class,
                () -> settingService.updateSetting(form));
        System.out.println("Update setting failed");
    }
}
