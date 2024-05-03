package de.garrafao.phitag.infrastructure.persistence.jpa.phase.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class PhaseQueryJpa implements Specification<Phase> {

    private final Query query;

    public PhaseQueryJpa(Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Phase> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Phase>> specifications = new ArrayList<>();

        for (final QueryComponent component : query.getComponents()) {
            if (component instanceof AnnotationTypeQueryComponent) {
                specifications.add(new AnnotationTypeQueryComponentSpecification(((AnnotationTypeQueryComponent) component).getAnnotationType()));
            } else if (component instanceof IsTutorialQueryComponent) {
                specifications.add(new IsTutorialQueryComponentSpecification(((IsTutorialQueryComponent) component).getTutorial()));
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
