package de.garrafao.phitag.infrastructure.persistence.jpa.tasks.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.tasks.Task;

public class PhaseQueryComponentSpecification implements Specification<Task> {

    private final String phase;

    public PhaseQueryComponentSpecification(final String phase) {
        this.phase = phase;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("phase").get("id").get("name"), phase);
        
    }
    
}
