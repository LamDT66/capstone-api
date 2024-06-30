package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Setting;
import com.fpt.entity.User;
import com.fpt.enums.Status;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	User findUserByEmail(String email);

	List<User> findAllUserByRoleAndStatus(Setting role, Status status);

	User getUserById(Long id);

	boolean existsByEmail(String email);

	boolean existsByMobile(String mobile);

	@Query("FROM User WHERE faculty.id = :facultyId AND role.id = 2")
	List<User> findManagersOfFaculty(@Param("facultyId") Long facultyId);
	
	@Query("FROM User WHERE faculty.id = :facultyId AND role.id = 3")
	List<User> findTeachersOfFaculty(@Param("facultyId") Long facultyId);
	
	@Query(value = "SELECT u.* "
			+ "FROM `user` u "
			+ "LEFT JOIN student_class sc ON sc.student_id = u.id "
			+ "WHERE sc.student_id IS NULL AND u.role_id = 4 and u.status = 'ACTIVE'", nativeQuery = true)
	List<User> findAllStudentsByNoClass();

	@Query(value = "SELECT u.* FROM user u\n" +
				   "LEFT JOIN student_project sp ON u.id = sp.student_id\n" +
				   "LEFT JOIN student_class sc ON u.id = sc.student_id\n" +
				   "WHERE u.role_id = 4 AND sp.project_id IS NULL AND sc.class_id = :classId AND u.status = 'ACTIVE'", nativeQuery = true)
	List<User> findAllStudentByNoProject(Long classId);
	
	@Query(value = "WITH projectTemp AS ( "
			+ "	SELECT 	project_id "
			+ " FROM  	student_project sp "
			+ " WHERE 	student_id = :studentId "
			+ ") "
			+ "SELECT u.* "
			+ "FROM   `user` u "
			+ "JOIN   student_project sp ON sp.student_id = u.id "
			+ "WHERE  sp.project_id IN (select project_id FROM projectTemp) AND u.role_id = 4 and u.status = 'ACTIVE'", nativeQuery = true)
	List<User> findAllStudentsByProject(@Param("studentId") Long studentId);
}
