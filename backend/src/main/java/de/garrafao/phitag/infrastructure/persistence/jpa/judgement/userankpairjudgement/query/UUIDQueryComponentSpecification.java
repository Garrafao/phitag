package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankpairjudgement.query;

import de.garrafao.phitag.domain.judgement.userankpairjudgement.UseRankPairJudgement;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UUIDQueryComponentSpecification implements Specification<UseRankPairJudgement> {

    private final String UUID;

    public UUIDQueryComponentSpecification(final String UUID) {
        this.UUID = UUID;
    }

    @Override
    public Predicate toPredicate(Root<UseRankPairJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("uuid"), UUID);
    }
    
}
