package de.garrafao.phitag.infrastructure.persistence.jpa.user.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.user.User;

public class FuzzyQueryComponentSpecification implements Specification<User> {

    private final String value;

    public FuzzyQueryComponentSpecification(final String value) {
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                criteriaBuilder.equal(root.get("username"), this.value),
                criteriaBuilder.equal(root.get("email"), this.value)
        );
    }
    
}
