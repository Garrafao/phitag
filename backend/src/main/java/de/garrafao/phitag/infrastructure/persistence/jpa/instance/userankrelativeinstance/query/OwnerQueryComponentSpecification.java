package de.garrafao.phitag.infrastructure.persistence.jpa.instance.userankrelativeinstance.query;

import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstance;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class OwnerQueryComponentSpecification implements Specification<UseRankRelativeInstance> {

    private final String owner;

    public OwnerQueryComponentSpecification(String owner) {
        this.owner = owner;
    }

    @Override
    public Predicate toPredicate(Root<UseRankRelativeInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("phaseid").get("projectid").get("ownername"), owner);
    }

}
