package de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotator.Annotator;

public class IsBotQueryComponentSpecification implements Specification<Annotator> {

    private final boolean isBot;

    public IsBotQueryComponentSpecification(boolean isBot) {
        this.isBot = isBot;
    }

    @Override
    public Predicate toPredicate(Root<Annotator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("user").get("isbot"), this.isBot);
    }
    
}
