package de.garrafao.phitag.infrastructure.persistence.jpa.report.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.report.Report;

public class StatusQueryComponentSpecification implements Specification<Report> {

    private final String status;

    public StatusQueryComponentSpecification(final String status) {
        this.status = status;
    }

    @Override
    public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("status"), status);
    }
    
}
