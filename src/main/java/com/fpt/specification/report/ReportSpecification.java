package com.fpt.specification.report;

import com.fpt.entity.Report;
import com.fpt.form.report.FilterReportForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

public class ReportSpecification {

    @SuppressWarnings("deprecation")
    public static Specification<Report> buildWhere(FilterReportForm filterForm) {

        Specification<Report> where = null;

        if (filterForm == null) return where;

        if (filterForm.getProjectId() != null) {
            ReportProjectSpecification projectId = new ReportProjectSpecification("projectId", filterForm.getProjectId());
            if (where == null) {
                where = projectId;
            } else {
                where = where.and(projectId);
            }
        }

        return where;
    }
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class ReportProjectSpecification implements Specification<Report> {

    @NonNull
    private String field;

    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (field.equalsIgnoreCase("projectId")) {
            return criteriaBuilder.equal(root.get("project").get("id"), value);
        }

        return null;
    }
}

