package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssiminstance.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;

public class OwnerQueryComponentSpecification implements Specification<WSSIMInstance> {

    private final String owner;

    public OwnerQueryComponentSpecification(final String owner) {
        this.owner = owner;
    }

    @Override
    public Predicate toPredicate(Root<WSSIMInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("phaseid").get("projectid").get("ownername"), owner);
    }
    
}
