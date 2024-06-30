package com.fpt.entity;

import java.time.LocalDateTime;

import com.fpt.enums.TokenType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@NoArgsConstructor
@Table(name = "token")
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Token extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String token;

	@Enumerated(EnumType.STRING)
	@Column(name = "token_type")
	private TokenType tokenType;

	@Column(name = "expired_date", columnDefinition = "DATETIME")
	private LocalDateTime expiredDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@PrePersist
	@Override
	public void setDefault() {
		super.setDefault();
		if (tokenType == null) {
			tokenType = TokenType.BEARER;
		}
	}
}