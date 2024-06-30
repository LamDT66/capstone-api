package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fpt.entity.Class;
import com.fpt.entity.Setting;
import com.fpt.entity.Subject;
import com.fpt.entity.User;
import com.fpt.enums.UserRole;
import com.fpt.form.clazz.AddClassForm;
import com.fpt.form.clazz.FilterClassForm;
import com.fpt.form.clazz.UpdateClassForm;
import com.fpt.repository.ClassRepository;
import com.fpt.service.ClassService;
import com.fpt.service.UserService;
import com.fpt.specification.clazz.ClassSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClassServiceImpl implements ClassService {

	@Autowired
	private ClassRepository repository;

	@Autowired
	private UserService userService;

	@Override
	public Page<Class> getAllClasses(Pageable pageable, String search, FilterClassForm filterForm) {
		User user = userService.getCurrentLoginUser();
		filterForm.setManagerId(user.getId());		
		Specification<Class> where = ClassSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}
	
	@Override
	public List<Class> getAllClassesForSelection() {
		User currentUser = userService.getCurrentLoginUser();
		String currentRole = currentUser.getRole().getSettingName();
		
		FilterClassForm filterForm = new FilterClassForm();
		
		if (currentRole.equals(UserRole.MANAGER.toString())) {
			filterForm.setManagerId(currentUser.getId());
		}		
		
		Specification<Class> where = ClassSpecification.buildWhere(null, filterForm);
		return repository.findAll(where);
	}

	@Override
	public List<Class> getAllClassesOfManager() {
		User currentLoginUser = userService.getCurrentLoginUser();
		return repository.getAllClassesAssignedForManager(currentLoginUser.getId());
	}
	
	@Override
	public Class getClassByID(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public boolean isClassExistsById(Long id) {
		return repository.existsById(id);
	}
	
	@Override
	public boolean isClassExistsByName(String name) {
		return repository.existsByName(name);
	}
	
	@Override
	public void createClass(AddClassForm form) {
		User creator = userService.getCurrentLoginUser();
		
		Class entity = Class.builder()
				.name(form.getName())
				.startDate(form.getStartDate())
				.endDate(form.getEndDate())
				.subject(Subject.builder().id(form.getSubjectId()).build())
				.semester(Setting.builder().id(form.getSemesterId()).build())
				.manager(User.builder().id(creator.getId()).build())
				.status(form.getStatus())
				.createdAt(LocalDateTime.now())
				.creator(creator)
				.modifiedAt(LocalDateTime.now())
				.modifier(creator)
				.build();

		repository.save(entity);
	}
	
	@Override
	public void updateClass(UpdateClassForm form) {
		Class entity = getClassByID(form.getId());
		entity.setName(form.getName());
		entity.setStartDate(form.getStartDate());
		entity.setEndDate(form.getEndDate());
		entity.setSemester(Setting.builder().id(form.getSemesterId()).build());
		entity.setStatus(form.getStatus());
		entity.setModifiedAt(LocalDateTime.now());
		entity.setModifier(userService.getCurrentLoginUser());
		
		repository.save(entity);
	}

	@Override
	public void deleteClass(Long id) {
		repository.deleteById(id);
	}
}
