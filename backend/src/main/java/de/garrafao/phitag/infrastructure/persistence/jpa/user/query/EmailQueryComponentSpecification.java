package de.garrafao.phitag.infrastructure.persistence.jpa.user.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.user.User;

public class EmailQueryComponentSpecification implements Specification<User> {

    private final String email;

    public EmailQueryComponentSpecification(String email) {
        this.email = email;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("email"), email);
    }

}
