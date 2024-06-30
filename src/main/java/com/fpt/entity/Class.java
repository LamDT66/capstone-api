package com.fpt.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fpt.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class Class extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(length = 50, nullable = false)
	private String name;
	
	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Column(name = "notification_sent")
	private boolean notificationSent;

	@ManyToOne
	@JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "semester_id", referencedColumnName = "id", nullable = false)
	private Setting semester;

	@ManyToOne
	@JoinColumn(name = "manager_id", referencedColumnName = "id", nullable = false)
	private User manager;
	
	@ManyToMany
	@JoinTable(
		name = "student_class",
		joinColumns = { @JoinColumn(name="class_id") },
		inverseJoinColumns = { @JoinColumn(name="student_id")}
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<User> students;
	
	@Enumerated(EnumType.STRING)
	private Status status;
}
