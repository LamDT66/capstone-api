package com.fpt.form.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateReportForm {

	@NotBlank
	private String title;

	@Positive
	private Long milestoneId;

	@NotBlank
	private String fileUrl;
}
