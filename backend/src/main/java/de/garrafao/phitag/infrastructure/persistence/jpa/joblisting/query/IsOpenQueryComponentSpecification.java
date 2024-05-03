package de.garrafao.phitag.infrastructure.persistence.jpa.joblisting.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.joblisting.Joblisting;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class IsOpenQueryComponentSpecification implements Specification<Joblisting> {

    private final boolean isOpen;

    public IsOpenQueryComponentSpecification(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public Predicate toPredicate(Root<Joblisting> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("open"), isOpen);
    }

}
