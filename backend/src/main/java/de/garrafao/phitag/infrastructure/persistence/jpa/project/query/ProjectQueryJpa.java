package de.garrafao.phitag.infrastructure.persistence.jpa.project.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.project.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class ProjectQueryJpa implements Specification<Project> {

    private final Query query;

    public ProjectQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Project>> specifications = new ArrayList<>();

        for (QueryComponent component : this.query.getComponents()) {

            if (component instanceof FuzzyQueryComponent) {
                specifications.add(new FuzzyQueryComponentSpecification(((FuzzyQueryComponent) component).getValue()));
            } else if (component instanceof IsActiveQueryComponent) {
                specifications
                        .add(new IsActiveQueryComponentSpecification(((IsActiveQueryComponent) component).getActive()));
            } else if (component instanceof LanguageQueryComponent) {
                specifications.add(
                        new LanguageQueryComponentSpecification(((LanguageQueryComponent) component).getLanguage()));
            } else if (component instanceof NameQueryComponent) {
                specifications.add(new NameQueryComponentSpecification(((NameQueryComponent) component).getName()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof VisibilityQueryComponent) {
                specifications.add(new VisibilityQueryComponentSpecification(
                        ((VisibilityQueryComponent) component).getVisibility()));
            }

        }

        return SpecificationCombiner.and(specifications).toPredicate(root, criteriaQuery, criteriaBuilder);
    }

}
