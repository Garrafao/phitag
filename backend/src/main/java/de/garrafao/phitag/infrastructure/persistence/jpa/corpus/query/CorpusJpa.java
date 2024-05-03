package de.garrafao.phitag.infrastructure.persistence.jpa.corpus.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.corpus.Corpus;
import de.garrafao.phitag.domain.corpus.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class CorpusJpa implements Specification<Corpus> {

    private final Query query;

    public CorpusJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Corpus> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Corpus>> specifications = new ArrayList();

        for (QueryComponent component : this.query.getComponents()) {
            if (component instanceof LexiconIdsQueryComponent) {
                specifications.add(new LexiconIdsQueryComponentSpecification(
                        ((LexiconIdsQueryComponent) component).getLexiconIds()));
            } else if (component instanceof BetweenDateQueryComponent) {
                specifications.add(new BetweenDateQueryComponentSpecification(
                        ((BetweenDateQueryComponent) component).getDateRange()));
            } else if (component instanceof CorpusNameQueryComponent) {
                specifications.add(
                        new CorpusNameQueryComponentSpecification(
                                ((CorpusNameQueryComponent) component).getCorpusnames()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);
    }

}
