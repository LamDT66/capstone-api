package com.fpt.specification.milestone_project;

import com.fpt.entity.Milestone;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ProjectMilestoneSpecification {

    @SuppressWarnings("deprecation")
    public static Specification<Milestone> buildWhere(Long id, String search){
        Specification<Milestone> where = null;

        CustomSpecification projectId = new CustomSpecification("projectId", id);
        where = Specification.where(projectId);

        if (!StringUtils.isEmpty(search)){
            search = search.trim();
            CustomSpecification title = new CustomSpecification("title", search);
            where = where.and(title.and(projectId));
        }

        return where;
    }
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Milestone> {

    @NonNull
    private String field;

    @NonNull Object value;

    @Override
    public Predicate toPredicate(Root<Milestone> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (field.equalsIgnoreCase("title")){
            return criteriaBuilder.like(root.get("title"), "%" + value.toString() + "%");
        }

        if (field.equalsIgnoreCase("projectId")) {
            return criteriaBuilder.equal(root.get("project").get("id"), value);
        }
        return null;
    }
}