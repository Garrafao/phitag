package de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotationtype.AnnotationType;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.user.User;

public class AnnotationTypeQueryComponentSpecification implements Specification<Annotator> {

    private final String name;

    public AnnotationTypeQueryComponentSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Annotator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate empty = criteriaBuilder.isEmpty(root.get("user").get("annotationTypes"));
        
        Join<Annotator, User> user = root.join("user");
        Join<User, AnnotationType> type = user.join("annotationTypes", JoinType.LEFT);

        Predicate inSet = criteriaBuilder.equal(type.get("name"), name);

        return criteriaBuilder.or(inSet, empty);
    }
    
}
