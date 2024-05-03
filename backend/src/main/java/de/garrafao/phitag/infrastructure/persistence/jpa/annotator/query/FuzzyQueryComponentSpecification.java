package de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotator.Annotator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FuzzyQueryComponentSpecification implements Specification<Annotator> {

    private final String value;

    public FuzzyQueryComponentSpecification(String value) {
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Annotator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String likeValue = "%" + this.value.toLowerCase() + "%";

        Expression<String> fieldUsername = root.get("id").get("username");
        Expression<String> fieldProject = root.get("id").get("projectid").get("name");
        Expression<String> fieldOwner = root.get("id").get("projectid").get("ownername");

        Predicate username = criteriaBuilder.like(criteriaBuilder.lower(fieldUsername.as(String.class)), likeValue);
        Predicate project = criteriaBuilder.like(criteriaBuilder.lower(fieldProject.as(String.class)), likeValue);
        Predicate owner = criteriaBuilder.like(criteriaBuilder.lower(fieldOwner.as(String.class)), likeValue);

        return criteriaBuilder.or(username, project, owner);
    }

}
