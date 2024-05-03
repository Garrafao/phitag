package de.garrafao.phitag.infrastructure.persistence.jpa.project.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.project.Project;

public class LanguageQueryComponentSpecification implements Specification<Project> {
    
    private final String language;

    public LanguageQueryComponentSpecification(String language) {
        this.language = language;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("language").get("name"), language);
    }

}
