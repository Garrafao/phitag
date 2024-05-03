package de.garrafao.phitag.infrastructure.persistence.jpa.phase.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.phase.Phase;

public class ProjectQueryComponentSpecification implements Specification<Phase> {

    private final String projectName;

    public ProjectQueryComponentSpecification(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public Predicate toPredicate(Root<Phase> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("projectid").get("name"), projectName);
    }

}
