package de.garrafao.phitag.infrastructure.persistence.jpa.phitagdata.usage.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.phitagdata.usage.Usage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProjectQueryComponentSpecification implements Specification<Usage> {

    private final String project;

    public ProjectQueryComponentSpecification(final String project) {
        this.project = project;
    }

    @Override
    public Predicate toPredicate(Root<Usage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("projectid").get("name"), project);
    }

}
