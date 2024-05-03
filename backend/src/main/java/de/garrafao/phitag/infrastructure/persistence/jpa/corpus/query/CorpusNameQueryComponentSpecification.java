package de.garrafao.phitag.infrastructure.persistence.jpa.corpus.query;

import java.util.List;

import javax.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.corpus.Corpus;


public class CorpusNameQueryComponentSpecification implements Specification<Corpus> {

    private final List<String> corpusnames;

    public CorpusNameQueryComponentSpecification(final List<String> corpusnames) {
        this.corpusnames = corpusnames;
    }

    @Override
    public Predicate toPredicate(Root<Corpus> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lower(root.get("corpusText").get("corpusInformation").get("corpusnameshort").as(String.class)).in(corpusnames.stream().map(String::toLowerCase).toList());
    }
    
}
