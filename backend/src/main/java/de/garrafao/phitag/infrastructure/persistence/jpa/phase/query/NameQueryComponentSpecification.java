package de.garrafao.phitag.infrastructure.persistence.jpa.phase.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.phase.Phase;

public class NameQueryComponentSpecification implements Specification<Phase> {

    private final String phaseName;

    public NameQueryComponentSpecification(String phaseName) {
        this.phaseName = phaseName;
    }

    @Override
    public Predicate toPredicate(Root<Phase> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("name"), phaseName);
    }

    
}
