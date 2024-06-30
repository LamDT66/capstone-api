package com.fpt.specification.clazz;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fpt.entity.Class;
import com.fpt.form.clazz.FilterClassForm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class ClassSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Class> buildWhere(String search, FilterClassForm filterForm) {

		Specification<Class> where = null;

		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification name = new CustomSpecification("name", search);
			where = Specification.where(name);
		}
		
		if(filterForm == null) return where;
		
		if(filterForm.getSubjectId() != null) {
			CustomSpecification subjectId = new CustomSpecification("subjectId", filterForm.getSubjectId());
			if (where == null) {
				where = subjectId;
			} else {
				where = where.and(subjectId);
			}
		}
		
		if(filterForm.getSemesterId() != null) {
			CustomSpecification semesterId = new CustomSpecification("semesterId", filterForm.getSemesterId());
			if (where == null) {
				where = semesterId;
			} else {
				where = where.and(semesterId);
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
class CustomSpecification implements Specification<Class> {

	@NonNull
	private String field;

	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(Root<Class> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("name")) {
			return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("subjectId")) {
			return criteriaBuilder.equal(root.get("subject").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("semesterId")) {
			return criteriaBuilder.equal(root.get("semester").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("managerId")) {
			return criteriaBuilder.equal(root.get("manager").get("id"), value);
		}
		
		if (field.equalsIgnoreCase("status")) {
			return criteriaBuilder.equal(root.get("status"), value);
		}
		
		return null;
	}
}
