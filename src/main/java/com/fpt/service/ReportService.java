package com.fpt.service;

import com.fpt.entity.Report;
import com.fpt.form.report.CreateReportForm;
import com.fpt.form.report.FilterReportForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {

	Page<Report> getReports(Pageable pageable, FilterReportForm filterForm);

	Report getReportById(Long id);

	void createReport(CreateReportForm reportForm);
}
