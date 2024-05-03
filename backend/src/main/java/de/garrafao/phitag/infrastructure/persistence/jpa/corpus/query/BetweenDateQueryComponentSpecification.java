package de.garrafao.phitag.infrastructure.persistence.jpa.corpus.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.corpus.Corpus;
import de.garrafao.phitag.domain.helper.Pair;

public class BetweenDateQueryComponentSpecification implements Specification<Corpus> {

    private final Integer from;
    private final Integer to;

    public BetweenDateQueryComponentSpecification(final Pair<Integer, Integer> dateRange) {
        this.from = dateRange.getLeft();
        this.to = dateRange.getRight();
    }

    @Override
    public Predicate toPredicate(Root<Corpus> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(root.get("corpusText").get("corpusInformation").get("date").as(Integer.class), from, to);
    }
    
}
