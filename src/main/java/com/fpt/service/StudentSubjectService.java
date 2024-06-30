package com.fpt.service;

import com.fpt.entity.StudentSubject;

import java.util.List;

public interface StudentSubjectService {
    List<StudentSubject> getStudentEmailBySubject(Long projectId);
}
