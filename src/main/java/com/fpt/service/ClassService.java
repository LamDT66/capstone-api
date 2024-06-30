package com.fpt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.entity.Class;
import com.fpt.form.clazz.AddClassForm;
import com.fpt.form.clazz.FilterClassForm;
import com.fpt.form.clazz.UpdateClassForm;

import java.util.List;

public interface ClassService {

	Page<Class> getAllClasses(Pageable pageable, String search, FilterClassForm filterForm);

	List<Class> getAllClassesForSelection();
	
	Class getClassByID(Long id);

	List<Class> getAllClassesOfManager();

	boolean isClassExistsById(Long id);

	boolean isClassExistsByName(String name);

	void createClass(AddClassForm form);
	
	void updateClass(UpdateClassForm form);
	
	void deleteClass(Long id);
}
