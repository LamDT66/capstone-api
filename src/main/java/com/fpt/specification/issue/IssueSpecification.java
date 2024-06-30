package com.fpt.specification.issue;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fpt.entity.Issue;
import com.fpt.form.issue.FilterIssueForm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class IssueSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Issue> buildWhere(String search, FilterIssueForm filterForm) {

		Specification<Issue> where = null;

		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification name = new CustomSpecification("title", search);
			where = Specification.where(name);
		}
		
		if(filterForm == null) return where;
		
		if(filterForm.getProjectId() != null) {
			CustomSpecification projectId = new CustomSpecification("projectId", filterForm.getProjectId());
			if (where == null) {
				where = projectId;
			} else {
				where = where.and(projectId);
			}
		}
		
		if(filterForm.getAssigneeId() != null) {
			CustomSpecification assigneeId = new CustomSpecification("assigneeId", filterForm.getAssigneeId());
			if (where == null) {
				where = assigneeId;
			} else {
				where = where.and(assigneeId);
			}
		}
		
		if(filterForm.getAssignerId() != null) {
			CustomSpecification assignerId = new CustomSpecification("assignerId", filterForm.getAssignerId());
			if (where == null) {
				where = assignerId;
			} else {
				where = where.and(assignerId);
			}
		}
		
		if(filterForm.getStatus() != null) {
			CustomSpecification status = new CustomSpecification("status", filterForm.getStatus());
			if (where == null) {
				where = status;
			} else {
				where = where.and(status);
			}
		}
		

		if(filterForm.getTeacherId() != null) {
			CustomSpecification mentorId = new CustomSpecification("mentorId", filterForm.getTeacherId());
			CustomSpecification comentorId = new CustomSpecification("comentorId", filterForm.getTeacherId());
			if (where == null) {
				where = mentorId.or(comentorId);
			} else {
				where = where.and(mentorId.or(comentorId));
			}
		}
		
		if(filterForm.getStudentId() != null) {
			CustomSpecification studentId = new CustomSpecification("studentId", filterForm.getStudentId());
			if (where == null) {
				where = studentId;
			} else {
				where = where.and(studentId);
			}
		}
		
		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Issue> {

	@NonNull
	private String field;

	@NonNull
	private Object value;

	@SuppressWarnings("rawtypes")
	@Override
	public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("title")) {
			return criteriaBuilder.like(root.get("title"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("projectId")) {
			return criteriaBuilder.equal(root.get("project").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("assigneeId")) {
			return criteriaBuilder.equal(root.get("assignee").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("assignerId")) {
			return criteriaBuilder.equal(root.get("assigner").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("status")) {
			return criteriaBuilder.equal(root.get("status"), value);
		}
		
		if (field.equalsIgnoreCase("mentorId")) {
			return criteriaBuilder.equal(root.get("project").get("mentor").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("comentorId")) {
			return criteriaBuilder.equal(root.get("project").get("coMentor").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("studentId")) {
			Join join = root.join("project").join("students", JoinType.RIGHT);
			return criteriaBuilder.equal(join.get("id"), value);
		}
		
		return null;
	}
}
