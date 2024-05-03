package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.entry.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.entry.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;;

public class DictionaryEntryQueryJpa implements Specification<DictionaryEntry> {

    private final Query query;

    public DictionaryEntryQueryJpa(Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<DictionaryEntry> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<DictionaryEntry>> specifications = new ArrayList<>();

        for (final QueryComponent component : this.query.getComponents()) {
            if (component instanceof HeadwordQueryComponent) {
                specifications.add(new HeadwordQueryComponentSpecification(((HeadwordQueryComponent) component).getHeadword()));
            } else if (component instanceof PartOfSpeachQueryComponent) {
                specifications.add(new PartOfSpeachQueryComponentSpecification(((PartOfSpeachQueryComponent) component).getPartOfSpeach()));
            } else if (component instanceof DictionarynameQueryComponent) {
                specifications.add(new DictionarynameQueryComponentSpecification(((DictionarynameQueryComponent) component).getName()));
            } else if (component instanceof DictionaryEntryIdQueryComponent) {
                specifications.add(new DictionaryEntryIdQueryComponentSpecification(((DictionaryEntryIdQueryComponent) component).getId()));
            } else if (component instanceof DictionaryOwnerQueryComponent) {
                specifications.add(new DictionaryOwnerQueryComponentSpecification(((DictionaryOwnerQueryComponent) component).getOwner()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);
    }

    

}
