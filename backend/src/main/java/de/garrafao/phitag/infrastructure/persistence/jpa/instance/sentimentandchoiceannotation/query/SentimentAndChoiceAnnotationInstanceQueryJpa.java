package de.garrafao.phitag.infrastructure.persistence.jpa.instance.sentimentandchoiceannotation.query;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.instance.sentimentandchoice.query.InstanceidQueryComponent;
import de.garrafao.phitag.domain.instance.sentimentandchoice.query.OwnerQueryComponent;
import de.garrafao.phitag.domain.instance.sentimentandchoice.query.PhaseQueryComponent;
import de.garrafao.phitag.domain.instance.sentimentandchoice.query.ProjectQueryComponent;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SentimentAndChoiceAnnotationInstanceQueryJpa implements Specification<SentimentAndChoiceInstance> {

    private final Query query;

    public SentimentAndChoiceAnnotationInstanceQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<SentimentAndChoiceInstance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<SentimentAndChoiceInstance>> specifications = new ArrayList<>();

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
