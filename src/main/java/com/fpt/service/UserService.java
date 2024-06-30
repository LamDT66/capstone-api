package com.fpt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fpt.entity.User;
import com.fpt.form.profile.UpdateProfileForm;
import com.fpt.form.student.ImportStudentForm.StudentForm;
import com.fpt.form.user.FilterUserForm;
import com.fpt.form.user.UpdateUserForm;

public interface UserService extends UserDetailsService {

	User getCurrentLoginUser();

	User getUserByEmail(String email);

	boolean isOldPasswordMatch(String oldPassword);

	Page<User> getAllUsers(Pageable pageable, String search, FilterUserForm filterForm);

	User getUserByID(Long id);

	void createUser(User user);
	
	List<Long> importStudents(List<StudentForm> studentForms);

	void updateUser(UpdateUserForm form);

	void deleteUserById(Long id);

	boolean isUserExistsByID(Long id);

	boolean isUserExistsByEmail(String email);

	boolean isUserExistsByMobile(String mobile);

	List<User> getAllManager();

	List<User> getAllTeachers();

	List<User> getAllStudent();

	User getUserById(Long id);

	List<User> getAllManagersOfFaculty(Long facultyId);

	List<User> getAllTeachersOfFaculty(Long facultyId);

	void updateProfile(UpdateProfileForm form);
}
