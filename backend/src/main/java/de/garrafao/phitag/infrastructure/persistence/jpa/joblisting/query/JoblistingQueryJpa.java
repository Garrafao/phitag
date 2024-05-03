package de.garrafao.phitag.infrastructure.persistence.jpa.joblisting.query;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.joblisting.Joblisting;
import de.garrafao.phitag.domain.joblisting.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class JoblistingQueryJpa implements Specification<Joblisting> {

    private final Query query;

    public JoblistingQueryJpa(Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Joblisting> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Joblisting>> specifications = new ArrayList<>();

        for (final QueryComponent component: query.getComponents()) {
            if (component instanceof FuzzyQueryComponent) {
                specifications.add(new FuzzyQueryComponentSpecification(((FuzzyQueryComponent) component).getValue()));
            } else if (component instanceof IsActiveQueryComponent) {
                specifications.add(new IsActiveQueryComponentSpecification(((IsActiveQueryComponent) component).isActive()));
            } else if (component instanceof IsOpenQueryComponent) {
                specifications.add(new IsOpenQueryComponentSpecification(((IsOpenQueryComponent) component).isOpen()));
            } else if (component instanceof NameQueryComponent) {
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
