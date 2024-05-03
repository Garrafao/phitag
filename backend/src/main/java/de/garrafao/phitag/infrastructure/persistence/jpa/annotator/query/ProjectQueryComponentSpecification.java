package de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotator.Annotator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProjectQueryComponentSpecification implements Specification<Annotator> {

    private final String project;

    public ProjectQueryComponentSpecification(String project) {
        this.project = project;
    }

    @Override
    public Predicate toPredicate(Root<Annotator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("projectid").get("name"), project);
    }

    
    
}
