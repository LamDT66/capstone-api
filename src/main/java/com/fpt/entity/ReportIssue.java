package com.fpt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "report_issue")
public class ReportIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Float actualEffort;

    private String milestoneTitle;

    private String typeSettingName;

    private String processSettingName;

    private String statusSettingName;

    private String assigneeFullName;

    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private Report report;
}
