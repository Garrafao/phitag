package de.garrafao.phitag.infrastructure.persistence.jpa.samplingorder.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformation;

public class PhaseQueryComponentSpecification implements Specification<AnnotationProcessInformation> {

    private final String phase;

    public PhaseQueryComponentSpecification(String phase) {
        this.phase = phase;
    }

    @Override
    public Predicate toPredicate(Root<AnnotationProcessInformation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("phaseid").get("name"), phase);
    }
    
}
