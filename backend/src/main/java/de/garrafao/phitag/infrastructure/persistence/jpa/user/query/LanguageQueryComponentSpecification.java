package de.garrafao.phitag.infrastructure.persistence.jpa.user.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.user.User;

public class LanguageQueryComponentSpecification implements Specification<User> {

    private final String language;

    public LanguageQueryComponentSpecification(final String language) {
        this.language = language;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join join = root.join("languages");
        return criteriaBuilder.equal(join.get("name"), language);
    }    
}
