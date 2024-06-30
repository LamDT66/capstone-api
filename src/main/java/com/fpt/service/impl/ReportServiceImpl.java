package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.fpt.config.internationalization.MessageProperty;
import com.fpt.entity.Project;
import com.fpt.repository.ProjectRepository;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fpt.entity.Milestone;
import com.fpt.entity.Report;
import com.fpt.entity.User;
import com.fpt.enums.UserRole;
import com.fpt.form.report.CreateReportForm;
import com.fpt.form.report.FilterReportForm;
import com.fpt.repository.ReportRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StudentService studentService;

    @Override
    public Page<Report> getReports(Pageable pageable, FilterReportForm filterForm) {
        User currentUser = userService.getCurrentLoginUser();
        String currentRole = currentUser.getRole().getSettingName();

        if (currentRole.equals(UserRole.TEACHER.toString())) {
            // teacher
            filterForm.setTeacherId(currentUser.getId());
            return reportRepository.getReportsByTeacher(currentUser.getId(), pageable);
        } else if (currentRole.equals(UserRole.STUDENT.toString())) {
            // student
            filterForm.setStudentId(currentUser.getId());
            return reportRepository.getReportsByStudent(currentUser.getId(), pageable);
        }
        
        return null;
    }

    @Override
    public Report getReportById(Long id) {
        return reportRepository.findById(id).get();
    }

    @Override
    public void createReport(CreateReportForm reportForm) {
        User creator = userService.getCurrentLoginUser();

        Report entity = Report.builder()
                .title(reportForm.getTitle())
                .milestone(Milestone.builder().id(reportForm.getMilestoneId()).build())
                .fileUrl(reportForm.getFileUrl())
                .project(projectService.getProjectByStudent())
                .createdAt(LocalDateTime.now())
				.creator(creator)
				.modifiedAt(LocalDateTime.now())
				.modifier(creator)
                .build();

        reportRepository.save(entity);

        String[] emailMembers = studentService.getMemberEmailByProject();
        String milestoneTitle = entity.getMilestone().getTitle();
        emailService.senEmailMultipleRecipients(
                emailMembers,
                reportForm.getTitle(),
                "Dear teacher,\n\n" + "We send you the report, please check in your report submission.\n\n" + "Best regards!"
               );
    }
}
