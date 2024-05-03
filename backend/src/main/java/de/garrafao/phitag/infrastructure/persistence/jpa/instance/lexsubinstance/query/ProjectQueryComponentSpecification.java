package de.garrafao.phitag.infrastructure.persistence.jpa.instance.lexsubinstance.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.instance.lexsub.LexSubInstance;

public class ProjectQueryComponentSpecification implements Specification<LexSubInstance> {

    private final String project;

    public ProjectQueryComponentSpecification(String project) {
        this.project = project;
    }

    @Override
    public Predicate toPredicate(Root<LexSubInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("phaseid").get("projectid").get("name"), project);
    }
    
}
