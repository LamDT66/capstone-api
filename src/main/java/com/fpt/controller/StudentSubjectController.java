package com.fpt.controller;

import com.fpt.dto.UserEmailForSelectDTO;
import com.fpt.entity.StudentSubject;
import com.fpt.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/students-subjects")
public class StudentSubjectController extends BaseController{

    @Autowired
    private StudentSubjectService studentSubjectService;

    @GetMapping(value = "email/{id}")
    public List<UserEmailForSelectDTO> getStudentEmailBySubject(@PathVariable(name = "id") Long id){
        return convertListEntityToListDto(studentSubjectService.getStudentEmailBySubject(id), UserEmailForSelectDTO.class);
    }
}
