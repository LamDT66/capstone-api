package com.fpt.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.fpt.entity.*;
import com.fpt.entity.Class;
import com.fpt.repository.StudentProjectRepository;
import com.fpt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fpt.form.student.AddStudentForm;
import com.fpt.form.student.FilterStudentClassForm;
import com.fpt.form.student.ImportStudentForm;
import com.fpt.repository.StudentClassRepository;
import com.fpt.repository.UserRepository;
import com.fpt.service.StudentService;
import com.fpt.service.UserService;
import com.fpt.specification.student.StudentClassSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentClassRepository studentClassRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private StudentProjectRepository studentProjectRepository;

	@Autowired
	private ProjectService projectService;


	@Override
	public Page<StudentClass> getAllStudentsByClass(Pageable pageable, Long classId, String search) {
		FilterStudentClassForm filterForm = new FilterStudentClassForm();
		filterForm.setClassId(classId);

		Specification<StudentClass> where = StudentClassSpecification.buildWhere(search, filterForm);
		return studentClassRepository.findAll(where, pageable);
	}
	
	@Override
	public List<User> getAllStudentsByNoClass() {
		return userRepository.findAllStudentsByNoClass();
	}
	
	@Override
	public List<User> getAllStudentsInProject() {
		User user = userService.getCurrentLoginUser();
		return userRepository.findAllStudentsByProject(user.getId());
	}

	@Override
	public void insertStudent(AddStudentForm studentForm) {
		List<StudentClass> entities = studentForm.getStudentIds().stream()
			.map(studentId -> StudentClass.builder()
					.student(User.builder().id(studentId).build())
					.clazz(Class.builder().id(studentForm.getClassId()).build())
					.build())
			.collect(Collectors.toList());
		studentClassRepository.saveAll(entities);
	}
	
	@Override
	public void importStudent(ImportStudentForm studentForm) {
		// create student
		List<Long> studentIds = userService.importStudents(studentForm.getStudents());
		
		// add student to class
		List<StudentClass> entities = studentIds.stream()
			.map(studentId -> StudentClass.builder()
					.student(User.builder().id(studentId).build())
					.clazz(Class.builder().id(studentForm.getClassId()).build())
					.build())
			.collect(Collectors.toList());
		studentClassRepository.saveAll(entities);
	}

	@Override
	public void removeStudent(Long studentId) {
		studentClassRepository.deleteByStudent(User.builder().id(studentId).build());
	}

	@Override
	public List<StudentProject> getAllStudentsByProject(Long id) {
		return studentProjectRepository.getStudentByProjectId(id);
	}

	@Override
	public List<User> getAllStudentsByNoProject(Long classId) {
		return userRepository.findAllStudentByNoProject(classId);
	}

	@Override
	public String[] getMemberEmailByProject() {
		Project project = projectService.getProjectByStudent();
		List<User> users = getAllStudentsInProject();
		String[] userEmails = null;
		if (project.getMentor().getId() == project.getCoMentor().getId()){
			userEmails = new String[users.size() + 1];
		} else {
			userEmails = new String[users.size() + 2];
		}
		int i = 0;
		for (User user : users) {
			userEmails[i] = user.getEmail();
			i++;
		}
		int length = userEmails.length;
		if (project.getMentor().getId() == project.getCoMentor().getId()){
			userEmails[length - 1] = project.getMentor().getEmail();
		} else {
			userEmails[length - 2] = project.getMentor().getEmail();
			userEmails[length - 1] = project.getCoMentor().getEmail();
		}
		return userEmails;
	}
}
