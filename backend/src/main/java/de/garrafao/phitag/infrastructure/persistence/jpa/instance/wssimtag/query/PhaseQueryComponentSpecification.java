package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssimtag.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;

public class PhaseQueryComponentSpecification implements Specification<WSSIMTag> {

    private final String phase;

    public PhaseQueryComponentSpecification(final String phase) {
        this.phase = phase;
    }

    @Override
    public Predicate toPredicate(Root<WSSIMTag> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("phaseid").get("name"), phase);
    }
    
}
