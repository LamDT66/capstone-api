package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Class;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long>, JpaSpecificationExecutor<Class> {

	boolean existsByName(String name);

	Class findByName(String name);

	@Query(value = "SELECT c FROM Class c WHERE c.manager.id = :id")
	List<Class> getAllClassesAssignedForManager(Long id);
}
