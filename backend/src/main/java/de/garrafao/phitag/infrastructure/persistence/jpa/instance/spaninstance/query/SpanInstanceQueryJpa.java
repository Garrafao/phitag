package de.garrafao.phitag.infrastructure.persistence.jpa.instance.spaninstance.query;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.instance.spaninstance.query.InstanceidQueryComponent;
import de.garrafao.phitag.domain.instance.spaninstance.query.OwnerQueryComponent;
import de.garrafao.phitag.domain.instance.spaninstance.query.PhaseQueryComponent;
import de.garrafao.phitag.domain.instance.spaninstance.query.ProjectQueryComponent;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstance;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SpanInstanceQueryJpa implements Specification<SpanInstance> {

    private final Query query;

    public SpanInstanceQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<SpanInstance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<SpanInstance>> specifications = new ArrayList<>();

        for (final QueryComponent component: query.getComponents()) {
            if (component instanceof InstanceidQueryComponent) {
                specifications.add(new InstanceIdQueryComponentSpecification(((InstanceidQueryComponent) component).getInstanceid()));
            } else if (component instanceof PhaseQueryComponent) {
                specifications.add(new PhaseQueryComponentSpecification(((PhaseQueryComponent) component).getPhase()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, criteriaQuery, criteriaBuilder);
    }

    
}
