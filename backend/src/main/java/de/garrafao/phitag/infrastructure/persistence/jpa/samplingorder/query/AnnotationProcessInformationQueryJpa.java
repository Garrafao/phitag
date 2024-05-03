package de.garrafao.phitag.infrastructure.persistence.jpa.samplingorder.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformation;
import de.garrafao.phitag.domain.annotationprocessinformation.query.*;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class AnnotationProcessInformationQueryJpa implements Specification<AnnotationProcessInformation> {

    private final Query query;

    public AnnotationProcessInformationQueryJpa(Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<AnnotationProcessInformation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<AnnotationProcessInformation>> specifications = new ArrayList<>();

        for (QueryComponent component : this.query.getComponents()) {
            if (component instanceof AnnotatorQueryComponent) {
                specifications.add(new AnnotatorQueryComponentSpecification(((AnnotatorQueryComponent) component).getAnnotator()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof PhaseQueryComponent) {
                specifications.add(new PhaseQueryComponentSpecification(((PhaseQueryComponent) component).getPhase()));
            } 
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);
    }

    
}
