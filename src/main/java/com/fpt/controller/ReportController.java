package com.fpt.controller;

import com.fpt.form.report.CreateReportForm;
import com.fpt.form.report.FilterReportForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fpt.dto.ReportDTO;
import com.fpt.entity.Report;
import com.fpt.service.ReportService;

@RestController
@RequestMapping(value = "api/v1/reports")
@Validated
public class ReportController extends BaseController {

	@Autowired
	private ReportService service;

	@GetMapping
	public Page<ReportDTO> getReports(Pageable pageable, FilterReportForm filterForm) {
		Page<Report> reportEntities = service.getReports(pageable, filterForm);
		return convertEntityPageToDtoPage(reportEntities, pageable, ReportDTO.class);
	}

	@GetMapping(value = "/{id}")
	public ReportDTO getReportById(@PathVariable("id") Long id) {
		Report reportEntity = service.getReportById(id);
		return convertEntityToDto(reportEntity, ReportDTO.class);
	}

	@PostMapping
	public String createReport(@RequestBody @Valid CreateReportForm form) {
		service.createReport(form);
		return "Create report successfully";
	}
}