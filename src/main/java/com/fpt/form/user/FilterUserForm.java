package com.fpt.form.user;

import com.fpt.enums.Status;
import com.fpt.enums.UserGender;

import lombok.Data;

@Data
public class FilterUserForm {

	private UserGender gender;

	private Long roleId;

	private Status status;
}
