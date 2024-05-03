package de.garrafao.phitag.infrastructure.persistence.jpa.project.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.project.Project;

public class FuzzyQueryComponentSpecification implements Specification<Project> {

    private final String value;

    public FuzzyQueryComponentSpecification(final String value) {
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String likeValue = "%" + this.value.toLowerCase() + "%";

        Expression<String> fieldName = root.get("id").get("name");
        Expression<String> fieldOwnerName = root.get("id").get("ownername");

        Predicate name = criteriaBuilder.like(criteriaBuilder.lower(fieldName.as(String.class)), likeValue);
        Predicate ownername = criteriaBuilder.like(criteriaBuilder.lower(fieldOwnerName.as(String.class)), likeValue);

        return criteriaBuilder.or(name, ownername);
    }

}
