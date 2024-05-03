package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.entry.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;

public class PartOfSpeachQueryComponentSpecification implements Specification<DictionaryEntry> {

    private final String partofspeech;

    public PartOfSpeachQueryComponentSpecification(final String partofspeech) {
        this.partofspeech = partofspeech;
    }

    @Override
    public Predicate toPredicate(Root<DictionaryEntry> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("partofspeech").get("name"), partofspeech);
    }
    
}
