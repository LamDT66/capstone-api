package com.fpt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.Subject;
import com.fpt.form.subject.AddSubjectForm;
import com.fpt.form.subject.FilterSubjectForm;
import com.fpt.form.subject.UpdateSubjectForm;

public interface SubjectService {
	Page<Subject> getAllSubject(Pageable pageable, String search, FilterSubjectForm filterForm);

	List<Subject> getAllSubjectForSelection();

	boolean isSubjectExistsByID(Long id);

	boolean isSubjectExistsByName(String name);

	Subject getSubjectByID(Long id);

	void createSubject(AddSubjectForm form);

	void updateSubject(UpdateSubjectForm form);

	void deleteSubject(Long id);
}