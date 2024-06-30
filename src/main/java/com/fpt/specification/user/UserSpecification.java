package com.fpt.specification.user;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fpt.entity.User;
import com.fpt.form.user.FilterUserForm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class UserSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<User> buildWhere(String search, FilterUserForm filterForm) {

		Specification<User> where = null;

		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification fullName = new CustomSpecification("fullName", search);
			CustomSpecification mobile = new CustomSpecification("mobile", search);
			CustomSpecification email = new CustomSpecification("email", search);
			where = Specification.where(fullName).or(mobile).or(email);
		}
		
		if(filterForm == null) return where;
		
		if(filterForm.getGender() != null) {
			CustomSpecification gender = new CustomSpecification("gender", filterForm.getGender());
			if (where == null) {
				where = gender;
			} else {
				where = where.and(gender);
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
		
		if(filterForm.getRoleId() != null) {
			CustomSpecification roleId = new CustomSpecification("roleId", filterForm.getRoleId());
			if (where == null) {
				where = roleId;
			} else {
				where = where.and(roleId);
			}
		}

		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<User> {

	@NonNull
	private String field;

	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("fullName")) {
			return criteriaBuilder.like(root.get("fullName"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("mobile")) {
			return criteriaBuilder.like(root.get("mobile"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("email")) {
			return criteriaBuilder.like(root.get("email"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("gender")) {
			return criteriaBuilder.equal(root.get("gender"), value);
		}
		
		if (field.equalsIgnoreCase("status")) {
			return criteriaBuilder.equal(root.get("status"), value);
		}
		
		if (field.equalsIgnoreCase("roleId")) {
			return criteriaBuilder.equal(root.get("role").get("id"), value);
		}
		
		return null;
	}
}
