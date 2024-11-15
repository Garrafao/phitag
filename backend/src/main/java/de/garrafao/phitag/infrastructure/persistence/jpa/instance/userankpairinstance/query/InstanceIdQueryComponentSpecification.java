package de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankpairinstance.query;

import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstance;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class InstanceIdQueryComponentSpecification implements Specification<UseRankPairInstance> {

private final String instanceid;

public InstanceIdQueryComponentSpecification(final String instanceid) {
        this.instanceid = instanceid;
    }

    @Override
    public Predicate toPredicate(Root<UseRankPairInstance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get("id").get("instanceid"), instanceid);
    }
}
