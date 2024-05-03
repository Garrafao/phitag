package de.garrafao.phitag.infrastructure.persistence.jpa.guideline.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.guideline.Guideline;
import de.garrafao.phitag.domain.guideline.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GuidelineQueryJpa implements Specification<Guideline> {

    private final Query query;

    public GuidelineQueryJpa(Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Guideline> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Guideline>> specifications = new ArrayList<>();
        
        for (final QueryComponent component: query.getComponents()) {
            if (component instanceof NameQueryComponent) {
                specifications.add(new NameQueryComponentSpecification(((NameQueryComponent) component).getName()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, criteriaQuery, criteriaBuilder);
    }

    
}
