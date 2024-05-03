package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.usepairjudgement.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgement;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AnnotatorQueryComponentSpecification implements Specification<UsePairJudgement> {

    private final String annotator;

    public AnnotatorQueryComponentSpecification(final String annotator) {
        this.annotator = annotator;
    }

    @Override
    public Predicate toPredicate(Root<UsePairJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("annotatorid").get("username"), annotator);
    }

    
}
