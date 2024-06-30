package com.fpt.entity;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@SuperBuilder
public class Report extends AbstractAuditingEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", length = 1000)
	private String title;

	@ManyToOne
	@JoinColumn(name = "milestone_id", referencedColumnName = "id", nullable = false)
	private Milestone milestone;

	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
	private Project project;

	@Column(name = "submit_time")
	@CreationTimestamp
	private LocalDateTime submitTime;

	@Column(name = "file_url")
	private String fileUrl;
}
