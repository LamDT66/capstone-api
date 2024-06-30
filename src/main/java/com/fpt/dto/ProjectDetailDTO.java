package com.fpt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ProjectDetailDTO {

	private String groupName;

	private String projectCode;

	private String englishName;

	private String vietnameseName;

	private Float estimateEffort;

	private String status;

	private String note;

	private ClassDTO clazz;

	private UserDTO leader;

	private UserDTO mentor;

	private UserDTO coMentor;

	private List<StudentProjectDTO> students;

	@Data
	@NoArgsConstructor
	static class UserDTO {
		private Long id;

		private String fullName;
	}

	@Data
	@NoArgsConstructor
	static class ClassDTO {
		private Long id;

		private String name;
	}

	@Data
	@NoArgsConstructor
	static class StudentProjectDTO {
		private Long id;

		private String fullName;

		private String email;
	}
}
