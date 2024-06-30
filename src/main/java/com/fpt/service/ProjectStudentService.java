package com.fpt.service;

import java.util.List;

import com.fpt.entity.StudentProject;

public interface ProjectStudentService {
    List<StudentProject> getMembersByProjectId(Long id);
}
