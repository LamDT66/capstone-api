package com.fpt.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@SuperBuilder
public abstract class AbstractAuditingEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id", nullable = true)
	private User creator;

	@Column(name = "created_at", columnDefinition = "DATETIME DEFAULT NOW()")
	@CreationTimestamp
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modifier_id", nullable = true)
	private User modifier;

	@Column(name = "modified_at", columnDefinition = "DATETIME DEFAULT NOW()")
	@UpdateTimestamp
	private LocalDateTime modifiedAt;

	@PrePersist
	public void setDefault() {
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
		if (modifiedAt == null) {
			modifiedAt = LocalDateTime.now();
		}
	}
}
