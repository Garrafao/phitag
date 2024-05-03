package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.wssim.query;

import de.garrafao.phitag.domain.tutorial.wssim.WSSIMTutorialJudgement;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AnnotatorQueryComponentSpecification implements Specification<WSSIMTutorialJudgement> {

    private final String annotator;

    public AnnotatorQueryComponentSpecification(final String annotator) {
        this.annotator = annotator;
    }

    @Override
    @Nullable
    public Predicate toPredicate(Root<WSSIMTutorialJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("annotatorid").get("username"), annotator);
    }
    
}
