package com.fpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.StudentProject;
import com.fpt.repository.StudentProjectRepository;
import com.fpt.service.ProjectStudentService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectStudentServiceImpl implements ProjectStudentService {

	@Autowired
	private StudentProjectRepository repository;

	@Override
	public List<StudentProject> getMembersByProjectId(Long id) {
//		return repository.getMembersByProjectId(id);
		return null;
	}
}
