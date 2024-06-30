package com.fpt.entity;

import com.fpt.enums.SettingType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Setting extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "setting_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private SettingType settingType;

	@Column(name = "setting_name", nullable = false, length = 50)
	private String settingName;

	@Column(name = "setting_display_order", nullable = false)
	private Integer settingDisplayOrder;

}
