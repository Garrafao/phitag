package de.garrafao.phitag.infrastructure.persistence.jpa.instance.spaninstance.query;

import de.garrafao.phitag.domain.instance.spaninstance.SpanInstance;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class InstanceIdQueryComponentSpecification implements Specification<SpanInstance> {

    private final String instanceid;

    public InstanceIdQueryComponentSpecification(final String instanceid) {
        this.instanceid = instanceid;
    }

    @Override
    public Predicate toPredicate(Root<SpanInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("instanceid"), instanceid);
    }
    
}
