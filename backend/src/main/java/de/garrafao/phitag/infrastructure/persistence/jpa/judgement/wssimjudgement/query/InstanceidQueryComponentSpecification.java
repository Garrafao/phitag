package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.wssimjudgement.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgement;

public class InstanceidQueryComponentSpecification implements Specification<WSSIMJudgement> {

    private final String instanceid;

    public InstanceidQueryComponentSpecification(final String instanceid) {
        this.instanceid = instanceid;
    }

    @Override
    @Nullable
    public Predicate toPredicate(Root<WSSIMJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("instanceid").get("instanceid"), instanceid);
    }
    
}
