package com.fpt.entity;

import com.fpt.enums.Status;
import com.fpt.enums.UserGender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class User extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 6, max = 50)
	@Column(nullable = false, unique = true)
	private String email;

	@Size(min = 6, max = 50)
	@Column(nullable = false, name = "full_name")
	private String fullName;

	@Size(min = 10, max = 20)
	private String mobile;

	@Enumerated(EnumType.STRING)
	private UserGender gender;

	private String avatar;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Setting role;

	@ManyToOne
	@JoinColumn(name = "faculty_id", referencedColumnName = "id")
	private Setting faculty;

	public String getFullName() {
		return fullName.toUpperCase();
	}
}
