package de.garrafao.phitag.infrastructure.persistence.jpa.joblisting.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.joblisting.Joblisting;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class IsActiveQueryComponentSpecification implements Specification<Joblisting> {

    private final boolean isActive;

    public IsActiveQueryComponentSpecification(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public Predicate toPredicate(Root<Joblisting> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("active"), isActive);
    }

}
