package de.garrafao.phitag.infrastructure.persistence.jpa.corpuslexicon.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.corpuslexicon.CorpusLexicon;

public class PoSQueryComponentSpecification implements Specification<CorpusLexicon> {

    private final String pos;

    public PoSQueryComponentSpecification(String pos) {
        this.pos = pos;
    }

    @Override
    public Predicate toPredicate(Root<CorpusLexicon> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("pos"), pos);
    }
    
}
