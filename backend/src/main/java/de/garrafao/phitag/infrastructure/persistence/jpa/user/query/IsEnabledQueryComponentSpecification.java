package de.garrafao.phitag.infrastructure.persistence.jpa.user.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.user.User;

public class IsEnabledQueryComponentSpecification implements Specification<User> {
    
    private final Boolean enabled;

    public IsEnabledQueryComponentSpecification(final Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Expression<Boolean> enabledExpression = root.get("enabled");

        return criteriaBuilder.equal(enabledExpression, enabled);
    }

}
