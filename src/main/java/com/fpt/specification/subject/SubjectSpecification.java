package com.fpt.specification.subject;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fpt.entity.Subject;
import com.fpt.form.subject.FilterSubjectForm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class SubjectSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Subject> buildWhere(String search, FilterSubjectForm filterForm) {

		Specification<Subject> where = null;

		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification subjectName = new CustomSpecification("subjectName", search);
			CustomSpecification faculty = new CustomSpecification("faculty", search);
			CustomSpecification managerFullname = new CustomSpecification("managerFullname", search);
			CustomSpecification managerEmail = new CustomSpecification("managerEmail", search);
			where = Specification.where(subjectName).or(faculty).or(managerFullname).or(managerEmail);
		}
		
		if(filterForm == null) return where;
		
		if(filterForm.getManagerId() != null) {
			CustomSpecification managerId = new CustomSpecification("managerId", filterForm.getManagerId());
			if (where == null) {
				where = managerId;
			} else {
				where = where.and(managerId);
			}
		}
		
		if(filterForm.getTeacherId() != null) {
			CustomSpecification teacherId = new CustomSpecification("teacherId", filterForm.getTeacherId());
			if (where == null) {
				where = teacherId;
			} else {
				where = where.and(teacherId);
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
		
		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Subject> {

	@NonNull
	private String field;

	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("subjectName")) {
			return criteriaBuilder.like(root.get("subjectName"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("faculty")) {
			return criteriaBuilder.like(root.get("faculty").get("settingName"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("managerFullname")) {
			return criteriaBuilder.like(root.get("manager").get("fullName"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("managerEmail")) {
			return criteriaBuilder.like(root.get("manager").get("email"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("managerId")) {
			return criteriaBuilder.equal(root.get("manager").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("teacherId")) {
			Join join = root.join("teachers", JoinType.LEFT);
			return criteriaBuilder.equal(join.get("id"), value);
		}
		if (field.equalsIgnoreCase("status")) {
			return criteriaBuilder.equal(root.get("status"), value);
		}
		
		return null;
	}
}
