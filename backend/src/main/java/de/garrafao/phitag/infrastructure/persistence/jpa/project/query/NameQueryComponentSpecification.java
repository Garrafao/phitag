package de.garrafao.phitag.infrastructure.persistence.jpa.project.query;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.project.Project;

public class NameQueryComponentSpecification implements Specification<Project> {
    
    private final String name;

    public NameQueryComponentSpecification(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("name"), name);
    }
    
}
