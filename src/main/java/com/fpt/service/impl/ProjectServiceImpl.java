package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fpt.entity.Class;
import com.fpt.enums.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fpt.entity.Project;
import com.fpt.entity.User;
import com.fpt.enums.UserRole;
import com.fpt.form.project.AddProjectForm;
import com.fpt.form.project.FilterProjectForm;
import com.fpt.form.project.UpdateProjectForm;
import com.fpt.repository.ProjectRepository;
import com.fpt.service.ProjectService;
import com.fpt.service.UserService;
import com.fpt.specification.project.ProjectSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Page<Project> getAllProjects(Pageable pageable, String search, FilterProjectForm filterForm) {
		User currentUser = userService.getCurrentLoginUser();
		String currentRole = currentUser.getRole().getSettingName();

		if (currentRole.equals(UserRole.MANAGER.toString())) {
			filterForm.setManagerId(currentUser.getId());
		} else if (currentRole.equals(UserRole.TEACHER.toString())) {
			filterForm.setTeacherId(currentUser.getId());
		} else {
			// student
			filterForm.setStudentId(currentUser.getId());
		}

		Specification<Project> where = ProjectSpecification.buildWhere(search, filterForm);
		return projectRepository.findAll(where, pageable);
	}
	
	@Override
	public Project getProjectByStudent() {
		User currentUser = userService.getCurrentLoginUser();
		FilterProjectForm filterForm = new FilterProjectForm();
		filterForm.setStudentId(currentUser.getId());
		Specification<Project> where = ProjectSpecification.buildWhere(null, filterForm);
		return projectRepository.findOne(where).get();
	}
	
	@Override
	public List<Project> getAllProjectsForSelection() {
		User currentUser = userService.getCurrentLoginUser();
		String currentRole = currentUser.getRole().getSettingName();

		FilterProjectForm filterForm = new FilterProjectForm();
		
		if (currentRole.equals(UserRole.TEACHER.toString())) {
			filterForm.setTeacherId(currentUser.getId());
		}
		
		Specification<Project> where = ProjectSpecification.buildWhere(null, filterForm);
		return projectRepository.findAll(where);
	}
	
	@Override
	public Project findById(Long id) {
		return projectRepository.findById(id).get();
	}

	@Override
	public boolean isProjectExistsByCode(String code) {
		return projectRepository.existsByProjectCode(code);
	}
	public List<Project> getAssignedProjects() {
		User currentUser = userService.getCurrentLoginUser();
		String currentRole = currentUser.getRole().getSettingName();
		if (currentRole.equals(UserRole.TEACHER.toString())) {
			return projectRepository.getAssignedProjectsForTeacher(currentUser.getId());
		} else if(currentRole.equals(UserRole.STUDENT.toString())){
			return projectRepository.getAssignedProjectsForStudent(currentUser.getId());
		}
		return null;
	}

	@Override
	public void addProject(AddProjectForm addProjectForm) {
		projectRepository
				.save(Project.builder()
						.clazz(Class.builder().id(addProjectForm.getClassId()).build())
						.groupName(addProjectForm.getGroupName())
						.projectCode(addProjectForm.getProjectCode())
						.vietnameseName(addProjectForm.getVietnameseName())
						.englishName(addProjectForm.getEnglishName())
						.mentor(User.builder().id(addProjectForm.getMentorId()).build())
						.coMentor(User.builder().id(addProjectForm.getCoMentorId()).build())
						.leader(addProjectForm.getLeaderId() != null ? User.builder().id(addProjectForm.getLeaderId()).build() : null)
						.status(addProjectForm.getStatus())
						.note(addProjectForm.getNote())
						.createdAt(LocalDateTime.now())
						.creator(userService.getCurrentLoginUser())
						.students(addProjectForm.getTeamMemberIds().stream()
								.map(id -> User.builder().id(id).build()).collect(Collectors.toList()))
				.build());
	}

	@Override
	public void updateProjectById(UpdateProjectForm updateProjectForm) {
		Project project = projectRepository.findById(updateProjectForm.getId()).get();
		project.setClazz(Class.builder().id(updateProjectForm.getClassId()).build());
		project.setGroupName(updateProjectForm.getGroupName());
		project.setProjectCode(updateProjectForm.getProjectCode());
		project.setVietnameseName(updateProjectForm.getVietnameseName());
		project.setEnglishName(updateProjectForm.getEnglishName());
		project.setMentor(User.builder().id(updateProjectForm.getMentorId()).build());
		project.setCoMentor(User.builder().id(updateProjectForm.getCoMentorId()).build());
		project.setLeader(updateProjectForm.getLeaderId() != null ? User.builder().id(updateProjectForm.getLeaderId()).build() : null);
		project.setStatus(updateProjectForm.getStatus());
		project.setNote(updateProjectForm.getNote());
		project.setModifier(userService.getCurrentLoginUser());
		project.setModifiedAt(LocalDateTime.now());
		project.setStudents(updateProjectForm.getTeamMemberIds().stream()
		.map(id -> User.builder().id(id).build()).collect(Collectors.toList()));
	}

	@Override
	public void importProjectByExcelFile(List<AddProjectForm> form) {
//		for (AddProjectForm projectImport : form) {
//			addProject(projectImport);
//		}
//		for (AddProjectForm projectImport : form) {
//			Class classEntity = classRepository.findById(projectImport.getClassId()).get();
//			User mentor = userService.getUserByID(projectImport.getMentorId());
//			User coMentor = userService.getUserByID(projectImport.getCoMentorId());
//			User leader = userService.getUserByID(projectImport.getLeaderId());
//			Project project = projectRepository.save(new Project(null, projectImport.getGroupName(),
//					projectImport.getProjectCode(), projectImport.getEnglishName(), projectImport.getVietnameseName(),
//					null, null, projectImport.getNote(), classEntity, leader, mentor, coMentor));
//			if (projectImport.getTeamMemberIds() != null) {
//				for (Long teamMemberId : projectImport.getTeamMemberIds()) {
//					projectStudentRepository
//							.save(new StudentProject(null, userService.getUserByID(teamMemberId), project));
//				}
//			}
//		}
	}

	@Override
	public void updateStatus(Long id) {
//		Project project = projectRepository.findById(id).get();
//		if (project.getStatus().toString().equals("ACTIVE")) {
//			project.setStatus(ProjectStatus.INACTIVE);
//		} else {
//			project.setStatus(ProjectStatus.ACTIVE);
//		}
//		projectRepository.save(project);
	}
}
