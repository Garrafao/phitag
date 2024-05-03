package de.garrafao.phitag.infrastructure.persistence.jpa.guideline.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.guideline.Guideline;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NameQueryComponentSpecification implements Specification<Guideline> {
    
    private final String name;
    
    public NameQueryComponentSpecification(String name) {
        this.name = name;
    }
    
    @Override
    public Predicate toPredicate(Root<Guideline> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("name"), name);
    }
    
}
