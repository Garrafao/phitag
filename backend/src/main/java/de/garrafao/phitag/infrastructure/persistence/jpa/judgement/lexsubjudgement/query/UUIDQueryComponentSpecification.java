package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.lexsubjudgement.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.judgement.lexsubjudgement.LexSubJudgement;

public class UUIDQueryComponentSpecification implements Specification<LexSubJudgement> {

    private final String UUID;

    public UUIDQueryComponentSpecification(final String UUID) {
        this.UUID = UUID;
    }

    @Override
    public Predicate toPredicate(Root<LexSubJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("uuid"), UUID);
    }
    
}
