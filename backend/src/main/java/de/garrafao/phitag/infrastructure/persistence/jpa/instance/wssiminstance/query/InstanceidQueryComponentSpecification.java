package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssiminstance.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;

public class InstanceidQueryComponentSpecification implements Specification<WSSIMInstance> {

    private final String instanceid;

    public InstanceidQueryComponentSpecification(final String instanceid) {
        this.instanceid = instanceid;
    }

    @Override
    public Predicate toPredicate(Root<WSSIMInstance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get("id").get("instanceid"), instanceid);
    }
    
}
