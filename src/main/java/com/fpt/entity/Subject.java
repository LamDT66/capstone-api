package com.fpt.entity;

import com.fpt.enums.Status;

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

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Subject extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "subject_name", nullable = false, unique = true, length = 50)
	private String subjectName;

	@Column(name = "verify_duration", nullable = false)
	private Integer verifyDuration;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "faculty_id", referencedColumnName = "id", nullable = false)
	private Setting faculty;

	@ManyToOne
	@JoinColumn(name = "manager_id", referencedColumnName = "id", nullable = false)
	private User manager;
}
