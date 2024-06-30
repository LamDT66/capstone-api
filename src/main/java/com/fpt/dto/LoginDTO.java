package com.fpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	private String token;

	private String refreshToken;

	@JsonProperty("user")
	private UserBasicInformationDTO basicInformationOfUser;
}
