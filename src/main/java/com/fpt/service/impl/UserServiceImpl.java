package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fpt.config.internationalization.MessageProperty;
import com.fpt.entity.Setting;
import com.fpt.entity.User;
import com.fpt.enums.Status;
import com.fpt.form.profile.UpdateProfileForm;
import com.fpt.form.student.ImportStudentForm.StudentForm;
import com.fpt.form.user.FilterUserForm;
import com.fpt.form.user.UpdateUserForm;
import com.fpt.repository.UserRepository;
import com.fpt.service.EmailService;
import com.fpt.service.UserService;
import com.fpt.specification.user.UserSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageProperty messageProperty;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findUserByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), 
				user.getPassword(), 
				AuthorityUtils.createAuthorityList(user.getRole().getSettingName()));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public User getCurrentLoginUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication == null) return null;
		String email = authentication.getName();
		return StringUtils.isEmpty(email) ? null : userRepository.findUserByEmail(email);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	@Override
	public boolean isOldPasswordMatch(String oldPassword) {
		return passwordEncoder.matches(oldPassword, getCurrentLoginUser().getPassword());
	}
	
	@Override
	public Page<User> getAllUsers(Pageable pageable, String search, FilterUserForm filterForm) {
		Specification<User> where = UserSpecification.buildWhere(search, filterForm);
		return userRepository.findAll(where, pageable);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.getUserById(id);
	}

	public boolean isUserExistsByID(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public boolean isUserExistsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean isUserExistsByMobile(String mobile) {
		return userRepository.existsByMobile(mobile);
	}

	@Override
	public User getUserByID(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void createUser(User user) {
		User creator = getCurrentLoginUser();
		// create random password
		String password = generateRandomPassword();
		
		user.setPassword(passwordEncoder.encode(password));
		user.setCreatedAt(LocalDateTime.now());
		user.setCreator(creator);
		user.setModifiedAt(LocalDateTime.now());
		user.setModifier(creator);
				
		user = userRepository.save(user);
				
		// sending mail
		emailService.sendEmail(
				user.getEmail(), 
				messageProperty.getMessage("email.send.admin.registration.subject.title"), 
				messageProperty.getMessage("email.send.admin.registration.subject.body") + user.getEmail() + " / " + password);		
	}

	@Override
	public List<Long> importStudents(List<StudentForm> studentForms) {
		User creator = getCurrentLoginUser();
		List<User> entities = new ArrayList<>();
		
		for (StudentForm form : studentForms) {
			String password = generateRandomPassword();
			form.setPassword(password);
			
			User entity = User.builder()
					.fullName(form.getFullName())
					.gender(form.getGender())
					.email(form.getEmail())
					.password(passwordEncoder.encode(password))
					.mobile(form.getMobile())
					.status(form.getStatus())
					.role(Setting.builder().id(4L).build()) // student
					.createdAt(LocalDateTime.now())
					.creator(creator)
					.modifiedAt(LocalDateTime.now())
					.modifier(creator)
					.build();
			
			entities.add(entity);
		}
		
		entities = userRepository.saveAll(entities);
				
		// sending mail
		for (StudentForm form : studentForms) {
			emailService.sendEmail(
					form.getEmail(), 
					messageProperty.getMessage("email.send.admin.registration.subject.title"), 
					messageProperty.getMessage("email.send.admin.registration.subject.body") + form.getEmail() + " / " + form.getPassword());		
		}
		
		// return list id of students
		return entities.stream().map(user -> user.getId()).collect(Collectors.toList());
	}
	
	private String generateRandomPassword() {
		// 6 --> 15 characters
		int charCount = new Random().nextInt(10) + 6;

		PasswordGenerator passwordGenerator = new PasswordGenerator();
		CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
		lowerCaseRule.setNumberOfCharacters(2);

		CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
		upperCaseRule.setNumberOfCharacters(2);

		return passwordGenerator.generatePassword(charCount, lowerCaseRule, upperCaseRule);
	}
	
	@Override
	public void updateUser(UpdateUserForm form) {
		User entity = getUserByID(form.getId());
		entity.setRole(Setting.builder().id(form.getRoleId()).build());
		entity.setStatus(form.getStatus());
		entity.setModifiedAt(LocalDateTime.now());
		entity.setModifier(getCurrentLoginUser());
		
		userRepository.save(entity);
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
	
	@Override
	public List<User> getAllManager() {
		return userRepository.findAllUserByRoleAndStatus(
				Setting.builder().id(2L).build(),
				Status.ACTIVE
				);
	}

	@Override
	public List<User> getAllTeachers() {
		return userRepository.findAllUserByRoleAndStatus(
				Setting.builder().id(3L).build(),
				Status.ACTIVE);
	}

	@Override
	public List<User> getAllStudent() {
		return userRepository.findAllUserByRoleAndStatus(
				Setting.builder().id(4L).build(),
				Status.ACTIVE);
	}

	@Override
	public List<User> getAllManagersOfFaculty(Long facultyId) {
		return userRepository.findManagersOfFaculty(facultyId);
	}
	
	@Override
	public List<User> getAllTeachersOfFaculty(Long facultyId) {
		return userRepository.findTeachersOfFaculty(facultyId);
	}

	@Override
	public void updateProfile(UpdateProfileForm form) {
		User user = getCurrentLoginUser();
	
		user.setFullName(form.getFullName());
		user.setGender(form.getGender());
		user.setMobile(form.getMobile());
		user.setFaculty(Setting.builder().id(form.getFacultyId()).build());
		user.setModifiedAt(LocalDateTime.now());
		user.setModifier(user);
				
		user = userRepository.save(user);
	}
}
