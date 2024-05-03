package de.garrafao.phitag.infrastructure.persistence.jpa.instance.usepairinstance.query;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import de.garrafao.phitag.domain.instance.usepairinstance.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class UsePairInstanceQueryJpa implements Specification<UsePairInstance> {

    private final Query query;

    public UsePairInstanceQueryJpa(Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<UsePairInstance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<UsePairInstance>> specifications = new ArrayList<>();

        for (final QueryComponent component : query.getComponents()) {
            if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof PhaseQueryComponent) {
                specifications.add(new PhaseQueryComponentSpecification(((PhaseQueryComponent) component).getPhase()));
            } else if (component instanceof InstanceidQueryComponent) {
                specifications.add(new InstanceidQueryComponentSpecification(((InstanceidQueryComponent) component).getInstanceid()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, criteriaQuery, criteriaBuilder);
    }

    
}
