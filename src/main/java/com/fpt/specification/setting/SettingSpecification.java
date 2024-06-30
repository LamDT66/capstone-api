package com.fpt.specification.setting;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fpt.entity.Setting;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class SettingSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Setting> buildWhere(String search) {

		Specification<Setting> where = null;

		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification name = new CustomSpecification("name", search);
			CustomSpecification type = new CustomSpecification("type", search);
			where = Specification.where(name).or(type);
		}

		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Setting> {

	@NonNull
	private String field;

	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(Root<Setting> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("name")) {
			return criteriaBuilder.like(root.get("settingName"), "%" + value.toString() + "%");
		}

		if (field.equalsIgnoreCase("type")) {
			return criteriaBuilder.like(root.get("settingType").as(String.class), "%" + value.toString() + "%");
		}

		return null;
	}
}
