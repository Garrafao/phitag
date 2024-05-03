package de.garrafao.phitag.infrastructure.persistence.jpa.instance.lexsubinstance.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.instance.lexsub.LexSubInstance;

public class OwnerQueryComponentSpecification implements Specification<LexSubInstance> {

    private final String owner;

    public OwnerQueryComponentSpecification(final String owner) {
        this.owner = owner;
    }

    @Override
    public Predicate toPredicate(Root<LexSubInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("phaseid").get("projectid").get("ownername"), owner);
    }

}
