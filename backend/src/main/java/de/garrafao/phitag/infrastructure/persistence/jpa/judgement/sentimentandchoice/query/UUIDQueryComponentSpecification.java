package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.sentimentandchoice.query;

import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgement;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UUIDQueryComponentSpecification implements Specification<SentimentAndChoiceJudgement> {

    private final String UUID;

    public UUIDQueryComponentSpecification(final String UUID) {
        this.UUID = UUID;
    }

    @Override
    public Predicate toPredicate(Root<SentimentAndChoiceJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("uuid"), UUID);
    }
    
}
