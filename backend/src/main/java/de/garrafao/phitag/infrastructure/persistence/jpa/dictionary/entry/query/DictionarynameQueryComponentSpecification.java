package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.entry.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;

public class DictionarynameQueryComponentSpecification implements Specification<DictionaryEntry> {
    
    private final String name;

    public DictionarynameQueryComponentSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<DictionaryEntry> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("dictionaryid").get("dname"), name);
    }

}
