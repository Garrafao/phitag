package de.garrafao.phitag.infrastructure.persistence.jpa.corpuslexicon.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.corpuslexicon.CorpusLexicon;
import de.garrafao.phitag.domain.corpuslexicon.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class CorpusLexiconJpa implements Specification<CorpusLexicon> {

    private final Query query;

    public CorpusLexiconJpa(Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<CorpusLexicon> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<CorpusLexicon>> specifications = new ArrayList<>();

        for (QueryComponent component : this.query.getComponents()) {
            if (component instanceof LikeTokenQueryComponent) {
                specifications.add(
                        new LikeTokenQueryComponentSpecification(((LikeTokenQueryComponent) component).getToken()));
            } else if (component instanceof LemmaQueryComponent) {
                specifications.add(new LemmaQueryComponentSpecification(((LemmaQueryComponent) component).getLemma()));
            } else if (component instanceof PoSQueryComponent) {
                specifications.add(new PoSQueryComponentSpecification(((PoSQueryComponent) component).getPos()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);
    }

}
