package de.garrafao.phitag.infrastructure.persistence.jpa.report.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.report.Report;

public class UserQueryComponentSpecification implements Specification<Report> {

    private final String username;

    public UserQueryComponentSpecification(final String username) {
        this.username = username;
    }

    @Override
    public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("user").get("username"), username);
    }
    
}
