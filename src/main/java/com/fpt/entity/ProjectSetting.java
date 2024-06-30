package com.fpt.entity;

import com.fpt.enums.ProjectSettingType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@Entity
@Table(name = "project_setting")
@SuperBuilder
public class ProjectSetting extends AbstractAuditingEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 3, max = 50)
	@Column(name = "setting_name")
	private String settingName;

	@Column(name = "setting_type")
	@Enumerated(EnumType.STRING)
	private ProjectSettingType settingType;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private Project project;
}
