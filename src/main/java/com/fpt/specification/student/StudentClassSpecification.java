package com.fpt.specification.student;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fpt.entity.StudentClass;
import com.fpt.form.student.FilterStudentClassForm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class StudentClassSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<StudentClass> buildWhere(String search, FilterStudentClassForm filterForm) {

		Specification<StudentClass> where = null;

		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification fullName = new CustomSpecification("fullName", search);
			CustomSpecification mobile = new CustomSpecification("mobile", search);
			CustomSpecification email = new CustomSpecification("email", search);
			where = Specification.where(fullName).or(mobile).or(email);
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
		
		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<StudentClass> {

	@NonNull
	private String field;

	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(Root<StudentClass> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("fullName")) {
			return criteriaBuilder.like(root.get("student").get("fullName"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("mobile")) {
			return criteriaBuilder.like(root.get("student").get("mobile"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("email")) {
			return criteriaBuilder.like(root.get("student").get("email"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("classId")) {
			return criteriaBuilder.equal(root.get("clazz").get("id"), value);
		}
		
		return null;
	}
}
