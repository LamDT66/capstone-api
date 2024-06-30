package com.fpt.specification.project_setting;

import com.fpt.entity.ProjectSetting;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class ProjectSettingSpecification {

    @SuppressWarnings("deprecation")
    public static Specification<ProjectSetting> buildWhere(String search) {

        Specification<ProjectSetting> where = null;

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
class CustomSpecification implements Specification<ProjectSetting> {

    @NonNull
    private String field;

    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(Root<ProjectSetting> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (field.equalsIgnoreCase("name")) {
            return criteriaBuilder.like(root.get("settingName"), "%" + value.toString() + "%");
        }

        if (field.equalsIgnoreCase("type")) {
            return criteriaBuilder.like(root.get("settingType").as(String.class), "%" + value.toString() + "%");
        }

        return null;
    }
}


