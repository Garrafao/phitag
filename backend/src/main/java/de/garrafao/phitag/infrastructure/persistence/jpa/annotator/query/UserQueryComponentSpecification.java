package de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotator.Annotator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserQueryComponentSpecification implements Specification<Annotator> {

    private final String user;

    public UserQueryComponentSpecification(String user) {
        this.user = user;
    }

    @Override
    public Predicate toPredicate(Root<Annotator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("username"), user);
    }
    
}
