package de.garrafao.phitag.infrastructure.persistence.jpa.instance.lexsubinstance.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.instance.lexsub.LexSubInstance;

public class InstanceIdQueryComponentSpecification implements Specification<LexSubInstance> {

    private final String instanceid;

    public InstanceIdQueryComponentSpecification(final String instanceid) {
        this.instanceid = instanceid;
    }

    @Override
    public Predicate toPredicate(Root<LexSubInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("instanceid"), instanceid);
    }
    
}
