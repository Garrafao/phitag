package de.garrafao.phitag.infrastructure.persistence.jpa.corpus.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.corpus.Corpus;

public class LexiconIdsQueryComponentSpecification implements Specification<Corpus> {

    private final List<String> lexiconIds;

    public LexiconIdsQueryComponentSpecification(final List<String> lexiconIds) {
        this.lexiconIds = lexiconIds;
    }

    @Override
    public Predicate toPredicate(Root<Corpus> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return root.get("corpusLexicon").get("id").in(lexiconIds);
    }

}
