package de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotator.Annotator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EntitlementQueryComponentSpecification implements Specification<Annotator> {

    private final String entitlement;

    public EntitlementQueryComponentSpecification(String entitlement) {
        this.entitlement = entitlement;
    }

    @Override
    public Predicate toPredicate(Root<Annotator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("entitlement").get("name"), entitlement);
    }

    
}
