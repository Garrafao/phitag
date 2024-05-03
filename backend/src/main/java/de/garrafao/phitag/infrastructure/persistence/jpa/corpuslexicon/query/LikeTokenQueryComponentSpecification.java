package de.garrafao.phitag.infrastructure.persistence.jpa.corpuslexicon.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.corpuslexicon.CorpusLexicon;

public class LikeTokenQueryComponentSpecification implements Specification<CorpusLexicon> {

    private final String token;

    public LikeTokenQueryComponentSpecification(String token) {
        this.token = token;
    }

    @Override
    public Predicate toPredicate(Root<CorpusLexicon> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String likeToken = token.toLowerCase() + "%";
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("token").as(String.class)), likeToken);
    }
    
}
