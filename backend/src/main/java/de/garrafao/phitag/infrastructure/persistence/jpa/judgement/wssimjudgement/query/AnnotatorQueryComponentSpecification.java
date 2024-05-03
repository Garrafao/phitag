package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.wssimjudgement.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgement;

public class AnnotatorQueryComponentSpecification implements Specification<WSSIMJudgement> {

    private final String annotator;

    public AnnotatorQueryComponentSpecification(final String annotator) {
        this.annotator = annotator;
    }

    @Override
    @Nullable
    public Predicate toPredicate(Root<WSSIMJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("annotatorid").get("username"), annotator);
    }
    
}
