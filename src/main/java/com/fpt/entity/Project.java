package com.fpt.entity;

import java.util.List;
import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;
import com.fpt.enums.ProjectStatus;
import jakarta.validation.constraints.Size;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Entity
public class Project extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "group_name", nullable = false, length = 30)
	private String groupName;

	@Column(name = "project_code", nullable = false, length = 20)
	private String projectCode;

	@Column(name = "english_name", nullable = false, length = 50)
	private String englishName;

	@Column(name = "vietnamese_name", length = 50)
	@Nationalized
	private String vietnameseName;

	@Column(name = "estimate_effort")
	private Float estimateEffort;

	@Enumerated(EnumType.STRING)
	private ProjectStatus status;
	
	@Column
	private String note;

	@ManyToOne
	@JoinColumn(name = "class_id", referencedColumnName = "id")
	private Class clazz;

	@ManyToOne
	@JoinColumn(name = "leader_id", referencedColumnName = "id")
	private User leader;

	@ManyToOne
	@JoinColumn(name = "mentor_id", referencedColumnName = "id")
	private User mentor;

	@ManyToOne
	@JoinColumn(name = "co_mentor_id", referencedColumnName = "id")
	private User coMentor;

	@ManyToMany
	@JoinTable(
			name = "student_project",
			joinColumns = { @JoinColumn(name="project_id") },
			inverseJoinColumns = { @JoinColumn(name="student_id")}
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<User> students;
}
