package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssiminstance.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;

public class PhaseQueryComponentSpecification implements Specification<WSSIMInstance> {

    private final String phase;

    public PhaseQueryComponentSpecification(final String phase) {
        this.phase = phase;
    }

    @Override
    public Predicate toPredicate(Root<WSSIMInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("phaseid").get("name"), phase);
    }
    
}
