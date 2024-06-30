package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fpt.entity.Setting;
import com.fpt.entity.Subject;
import com.fpt.entity.User;
import com.fpt.form.subject.AddSubjectForm;
import com.fpt.form.subject.FilterSubjectForm;
import com.fpt.form.subject.UpdateSubjectForm;
import com.fpt.repository.SubjectRepository;
import com.fpt.service.SubjectService;
import com.fpt.service.UserService;
import com.fpt.specification.subject.SubjectSpecification;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private UserService userService;

	@Override
	public Page<Subject> getAllSubject(Pageable pageable, String search, FilterSubjectForm filterForm) {
		Specification<Subject> where = SubjectSpecification.buildWhere(search, filterForm);
		return subjectRepository.findAll(where, pageable);
	}

	@Override
	public List<Subject> getAllSubjectForSelection() {
		return subjectRepository.findAll();
	}

	@Override
	public boolean isSubjectExistsByID(Long id) {
		return subjectRepository.existsById(id);
	}

	@Override
	public boolean isSubjectExistsByName(String name) {
		return subjectRepository.existsBySubjectName(name);
	}

	@Override
	public Subject getSubjectByID(Long id) {
		return subjectRepository.findById(id).get();
	}

	@Override
	public void createSubject(AddSubjectForm form) {
		User creator = userService.getCurrentLoginUser();
		
		Subject subject = Subject.builder()
				.subjectName(form.getName())
				.verifyDuration(form.getDuration())
				.status(form.getStatus())
				.faculty(Setting.builder().id(form.getFacultyId()).build())
				.manager(User.builder().id(form.getManagerId()).build())
				.createdAt(LocalDateTime.now())
				.creator(creator)
				.modifiedAt(LocalDateTime.now())
				.modifier(creator)
				.build();

		subjectRepository.save(subject);
	}

	@Override
	public void updateSubject(UpdateSubjectForm form) {
		Subject entity = getSubjectByID(form.getId());
		entity.setSubjectName(form.getName());
		entity.setVerifyDuration(form.getDuration());
		entity.setStatus(form.getStatus());
		entity.setFaculty(Setting.builder().id(form.getFacultyId()).build());
		entity.setManager(User.builder().id(form.getManagerId()).build());
		entity.setModifiedAt(LocalDateTime.now());
		entity.setModifier(userService.getCurrentLoginUser());

		subjectRepository.save(entity);
	}

	@Override
	public void deleteSubject(Long id) {
		subjectRepository.deleteById(id);
	}
}
