package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.entry.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;

public class HeadwordQueryComponentSpecification implements Specification<DictionaryEntry> {

    private final String headword;

    public HeadwordQueryComponentSpecification(final String headword) {
        this.headword = headword;
    }

    @Override
    public Predicate toPredicate(Root<DictionaryEntry> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("headword")), headword.toLowerCase() + "%");
    }

}
