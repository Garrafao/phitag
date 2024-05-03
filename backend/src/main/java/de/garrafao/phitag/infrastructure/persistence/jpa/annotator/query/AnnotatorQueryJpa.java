package de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.annotator.query.*;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class AnnotatorQueryJpa implements Specification<Annotator> {
    
    private final Query query;
    
    public AnnotatorQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Annotator> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Annotator>> specifications = new ArrayList<>();

        for (QueryComponent component: this.query.getComponents()) {

            if (component instanceof FuzzyQueryComponent) {
                specifications.add(new FuzzyQueryComponentSpecification(((FuzzyQueryComponent) component).getValue()));
            } else if (component instanceof EntitlementQueryComponent) {
                specifications.add(new EntitlementQueryComponentSpecification(((EntitlementQueryComponent) component).getEntitlement()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof UserQueryComponent) {
                specifications.add(new UserQueryComponentSpecification(((UserQueryComponent) component).getUser()));
            } else if (component instanceof IsBotQueryComponent) {
                specifications.add(new IsBotQueryComponentSpecification(((IsBotQueryComponent) component).isBot()));
            } else if (component instanceof AnnotationTypeQueryComponent) {
                specifications.add(new AnnotationTypeQueryComponentSpecification(((AnnotationTypeQueryComponent) component).getName()));
            } else if (component instanceof LanguageQueryComponent) {
                specifications.add(new LanguageQueryComponentSpecification(((LanguageQueryComponent) component).getLanguage()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, criteriaQuery, criteriaBuilder);
    }

    
}
