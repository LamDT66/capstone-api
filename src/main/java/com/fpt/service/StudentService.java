package com.fpt.service;

import java.util.List;

import com.fpt.entity.StudentProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.StudentClass;
import com.fpt.entity.User;
import com.fpt.form.student.AddStudentForm;
import com.fpt.form.student.ImportStudentForm;

public interface StudentService {

	Page<StudentClass> getAllStudentsByClass(Pageable pageable, Long classId, String search);

	List<User> getAllStudentsByNoClass();
	
	List<User> getAllStudentsInProject();

	void insertStudent(AddStudentForm studentForm);

	void importStudent(ImportStudentForm studentForm);

	void removeStudent(Long studentId);

	List<StudentProject> getAllStudentsByProject(Long id);

	List<User> getAllStudentsByNoProject(Long classId);

	String[] getMemberEmailByProject();
}
