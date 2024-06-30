package com.fpt.repository;

import com.fpt.entity.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {

    @Query(value = "SELECT ss FROM StudentSubject ss")
    List<StudentSubject> getStudentEmailBySubject(Long id);
}
