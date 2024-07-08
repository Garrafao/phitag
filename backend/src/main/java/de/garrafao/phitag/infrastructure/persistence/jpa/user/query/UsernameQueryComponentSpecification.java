package de.garrafao.phitag.infrastructure.persistence.jpa.user.query;

import de.garrafao.phitag.domain.user.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UsernameQueryComponentSpecification implements Specification<User> {

    private final String value;

    public UsernameQueryComponentSpecification(final String value) {
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        // Use 'like' with '%' wildcard at the beginning and end to match any part of the username
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + this.value.toLowerCase() + "%");
    }
}
