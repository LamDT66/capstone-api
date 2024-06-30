package com.fpt.controller;

import java.util.List;

import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.dto.UserBasicInformationDTO;
import com.fpt.dto.UserDTO;
import com.fpt.dto.UserForSelectDTO;
import com.fpt.entity.User;
import com.fpt.form.profile.UpdateProfileForm;
import com.fpt.form.user.AddUserForm;
import com.fpt.form.user.FilterUserForm;
import com.fpt.form.user.UpdateUserForm;
import com.fpt.service.UserService;
import com.fpt.validation.form.user.UserIDExists;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController extends BaseController {

	@Autowired
	private UserService service;

	@GetMapping(value = "/email/exists")
	public boolean isEmailExists(String email) {
		return service.isUserExistsByEmail(email);
	}

	@GetMapping(value = "/mobile/exists")
	public Boolean isUserMobileExists(String mobile) {
		return service.isUserExistsByMobile(mobile);
	}

	@GetMapping()
	public Page<UserDTO> getAllUsers(Pageable pageable, 
			@RequestParam(value = "q", required = false) String search,
			FilterUserForm filterForm) {
		Page<User> entityPage = service.getAllUsers(pageable, search, filterForm);
		Page<UserDTO> dtoPage = convertEntityPageToDtoPage(entityPage, pageable, UserDTO.class);
		return dtoPage;
	}

	@GetMapping(value = "/{id}")
	public UserDTO getUserById(@PathVariable(name = "id") @UserIDExists Long id) {
		User entityUser = service.getUserByID(id);
		UserDTO dto = convertEntityToDto(entityUser, UserDTO.class);
		return dto;
	}

	@PostMapping
	public void createUser(@RequestBody @Valid AddUserForm userForm) {
		User user = convertUserFormToUser(userForm);
		service.createUser(user);
	}

	private User convertUserFormToUser(AddUserForm userForm) {
		// omit id field
		TypeMap<AddUserForm, User> typeMap = modelMapper.getTypeMap(AddUserForm.class, User.class);
		if (typeMap == null) { // if not already added
			// skip field
			modelMapper.addMappings(new PropertyMap<AddUserForm, User>() {
				@Override
				protected void configure() {
					skip(destination.getId());
				}
			});
		}
		return modelMapper.map(userForm, User.class);
	}

	@PutMapping(value = "/{id}")
	public void updateUser(@PathVariable(name = "id") @UserIDExists Long id,
			@RequestBody @Valid UpdateUserForm userForm) {
		userForm.setId(id);
		service.updateUser(userForm);
	}

	@DeleteMapping(value = "/{id}")
	public String deleteUserById(@PathVariable(name = "id") @UserIDExists Long id) {
		service.deleteUserById(id);
		return "Delete successfully!";
	}

	@GetMapping("/profile")
	public UserBasicInformationDTO getProfile() {
		return convertEntityToDto(service.getCurrentLoginUser(), UserBasicInformationDTO.class);
	}

	@PutMapping("/profile")
	public String updateProfile(@RequestBody @Valid UpdateProfileForm form) {
		service.updateProfile(form);
		return "Update successfully!";
	}

	@GetMapping("/managers/{facultyId}")
	public List<UserForSelectDTO> getAllManagerOfFaculty(@PathVariable(name = "facultyId") @UserIDExists Long facultyId) {
		return convertListEntityToListDto(service.getAllManagersOfFaculty(facultyId), UserForSelectDTO.class);
	}

	@GetMapping("/teachers/{facultyId}")
	public List<UserForSelectDTO> getAllTeachersOfFaculty(@PathVariable(name = "facultyId") @UserIDExists Long facultyId) {
		return convertListEntityToListDto(service.getAllTeachersOfFaculty(facultyId), UserForSelectDTO.class);
	}

	@GetMapping("/teachers/current-faculty")
	public List<UserForSelectDTO> getAllTeacherOfCurrentFaculty(){
		Long currentFaculty = service.getCurrentLoginUser().getFaculty().getId();
		return convertListEntityToListDto(service.getAllTeachersOfFaculty(currentFaculty), UserForSelectDTO.class);
	}
	
	@GetMapping("/managers")
	public List<UserForSelectDTO> getAllManagers() {
		return convertListEntityToListDto(service.getAllManager(), UserForSelectDTO.class);
	}

	@GetMapping("/teachers")
	public List<UserForSelectDTO> getAllTeachersByCurrentFaculty() {
		return convertListEntityToListDto(service.getAllTeachers(), UserForSelectDTO.class);
	}

	@GetMapping("/students")
	public List<UserForSelectDTO> getAllStudents() {
		return convertListEntityToListDto(service.getAllStudent(), UserForSelectDTO.class);
	}
}
