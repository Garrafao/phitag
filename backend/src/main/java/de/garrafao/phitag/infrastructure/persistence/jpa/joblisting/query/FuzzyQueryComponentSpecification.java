package de.garrafao.phitag.infrastructure.persistence.jpa.joblisting.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.joblisting.Joblisting;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FuzzyQueryComponentSpecification implements Specification<Joblisting> {

    private final String fuzzy;

    public FuzzyQueryComponentSpecification(String fuzzy) {
        this.fuzzy = fuzzy;
    }

    @Override
    public Predicate toPredicate(Root<Joblisting> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String likeValue = "%" + this.fuzzy.toLowerCase() + "%";

        Expression<String> fieldName = root.get("id").get("name");
        Expression<String> fieldProjectName = root.get("id").get("projectid").get("name");
        Expression<String> fieldProjectOwnerName = root.get("id").get("projectid").get("ownername");
     
        Predicate name = criteriaBuilder.like(criteriaBuilder.lower(fieldName.as(String.class)), likeValue);
        Predicate projectName = criteriaBuilder.like(criteriaBuilder.lower(fieldProjectName.as(String.class)), likeValue);
        Predicate projectOwnerName = criteriaBuilder.like(criteriaBuilder.lower(fieldProjectOwnerName.as(String.class)), likeValue);

        return criteriaBuilder.or(name, projectName, projectOwnerName);
    }
    
}
