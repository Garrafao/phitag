package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userankrelative.query;

import de.garrafao.phitag.domain.tutorial.userankrelative.UseRankRelativeTutorialJudgement;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OwnerQueryComponentSpecification implements Specification<UseRankRelativeTutorialJudgement> {

    private final String owner;

    public OwnerQueryComponentSpecification(final String owner) {
        this.owner = owner;
    }

    @Override
    public Predicate toPredicate(Root<UseRankRelativeTutorialJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("instanceid").get("phaseid").get("projectid").get("ownername"), owner);
    }
    
}
