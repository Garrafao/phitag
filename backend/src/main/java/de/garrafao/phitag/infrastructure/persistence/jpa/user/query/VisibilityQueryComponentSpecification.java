package de.garrafao.phitag.infrastructure.persistence.jpa.user.query;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.user.User;

public class VisibilityQueryComponentSpecification implements Specification<User> {

    private final String visibility;

    public VisibilityQueryComponentSpecification(final String visibility) {
        this.visibility = visibility;
    }

    @Override
    public javax.persistence.criteria.Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Expression<String> visibilityExpression = root.get("visibility").get("name");

        return criteriaBuilder.equal(visibilityExpression, visibility);
    }

    
}
