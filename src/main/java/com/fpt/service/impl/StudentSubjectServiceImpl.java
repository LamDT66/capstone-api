package com.fpt.service.impl;

import com.fpt.entity.StudentSubject;
import com.fpt.repository.ProjectRepository;
import com.fpt.repository.StudentProjectRepository;
import com.fpt.repository.StudentSubjectRepository;
import com.fpt.service.StudentSubjectService;
import com.fpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSubjectServiceImpl implements StudentSubjectService {

    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    @Autowired
    private StudentProjectRepository projectStudentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<StudentSubject> getStudentEmailBySubject(Long projectId) {

//        User currentLoginUser = userService.getCurrentLoginUser();
//        List<StudentSubject> studentSubjects = currentLoginUser.getRole().getSettingName().equals(UserRole.MANAGER.toString())?studentSubjectRepository.getStudentEmailBySubject(currentLoginUser.getId()):null;
//        if (studentSubjects == null){
//            return null;
//        }
//        List<StudentSubject> studentSubjectsNoProject = new ArrayList<>();
//        for (StudentSubject studentSubject : studentSubjects) {
//            for (StudentProject p : projectStudentRepository.getMembersByProjectId(projectId)) {
//                if (p.getStudent().getId() == studentSubject.getStudent().getId()){
//                    studentSubjectsNoProject.add(studentSubject);
//                }
//            }
//            if (projectStudentRepository.getProjectByStudentId(studentSubject.getStudent().getId()).isEmpty() && projectRepository.getProjectByLeaderId(studentSubject.getStudent().getId()).isEmpty()){
//                studentSubjectsNoProject.add(studentSubject);
//            }
//        }
//
//
//
//        return studentSubjectsNoProject;
    	return null;
    }
}
