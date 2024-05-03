package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userank.query;

import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgement;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AnnotatorQueryComponentSpecification implements Specification<UseRankTutorialJudgement> {

    private final String annotator;

    public AnnotatorQueryComponentSpecification(final String annotator) {
        this.annotator = annotator;
    }

    @Override
    public Predicate toPredicate(Root<UseRankTutorialJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("annotatorid").get("username"), annotator);
    }

    
}
