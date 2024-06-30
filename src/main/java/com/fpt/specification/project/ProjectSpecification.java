package com.fpt.specification.project;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fpt.entity.Project;
import com.fpt.form.project.FilterProjectForm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class ProjectSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Project> buildWhere(String search, FilterProjectForm filterForm) {

		Specification<Project> where = null;

		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification englishName = new CustomSpecification("englishName", search);
			where = Specification.where(englishName);
		}
		
		if(filterForm == null) return where;
		
		if(filterForm.getClassId() != null) {
			CustomSpecification classId = new CustomSpecification("classId", filterForm.getClassId());
			if (where == null) {
				where = classId;
			} else {
				where = where.and(classId);
			}
		}
		
		if(filterForm.getManagerId() != null) {
			CustomSpecification managerId = new CustomSpecification("managerId", filterForm.getManagerId());
			if (where == null) {
				where = managerId;
			} else {
				where = where.and(managerId);
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
class CustomSpecification implements Specification<Project> {

	@NonNull
	private String field;

	@NonNull
	private Object value;

	@SuppressWarnings("rawtypes")
	@Override
	public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("englishName")) {
			return criteriaBuilder.like(root.get("englishName"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("classId")) {
			return criteriaBuilder.equal(root.get("clazz").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("managerId")) {
			return criteriaBuilder.equal(root.get("clazz").get("manager").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("mentorId")) {
			return criteriaBuilder.equal(root.get("mentor").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("comentorId")) {
			return criteriaBuilder.equal(root.get("coMentor").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("studentId")) {
			Join join = root.join("students", JoinType.RIGHT);
			return criteriaBuilder.equal(join.get("id"), value);
		}
		return null;
	}
}
