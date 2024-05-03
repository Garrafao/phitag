package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssiminstance.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;
import de.garrafao.phitag.domain.instance.wssiminstance.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class WSSIMInstanceQueryJpa implements Specification<WSSIMInstance> {

    private final Query query;

    public WSSIMInstanceQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<WSSIMInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<WSSIMInstance>> specifications = new ArrayList<>();

        for (final QueryComponent component : this.query.getComponents()) {
            if (component instanceof InstanceidQueryComponent) {
                specifications.add(new InstanceidQueryComponentSpecification(
                        ((InstanceidQueryComponent) component).getInstanceid()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications
                        .add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof PhaseQueryComponent) {
                specifications.add(new PhaseQueryComponentSpecification(((PhaseQueryComponent) component).getPhase()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);
    }

}
