package com.fpt.entity;

import com.fpt.enums.IssueStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Issue extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 200)
	private String title;

	@Column(nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "milestone_id", referencedColumnName = "id")
	private Milestone milestone;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "type_id", referencedColumnName = "id")
	private ProjectSetting type;

	@ManyToOne
	@JoinColumn(name = "process_id", referencedColumnName = "id")
	private ProjectSetting process;

	@ManyToOne
	@JoinColumn(name = "assignee_id", referencedColumnName = "id")
	private User assignee;

	@ManyToOne
	@JoinColumn(name = "assigner_id", referencedColumnName = "id")
	private User assigner;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private IssueStatus status;
}
