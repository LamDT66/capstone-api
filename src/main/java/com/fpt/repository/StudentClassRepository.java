package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fpt.entity.StudentClass;
import com.fpt.entity.User;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long>, JpaSpecificationExecutor<StudentClass> {
	void deleteByStudent(User student);
}
