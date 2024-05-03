package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.wssimjudgement.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.garrafao.phitag.domain.judgement.common.query.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgement;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class WSSIMJudgementQueryJpa implements Specification<WSSIMJudgement> {

    private final Query query;

    public WSSIMJudgementQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    @Nullable
    public Predicate toPredicate(Root<WSSIMJudgement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<WSSIMJudgement>> specifications = new ArrayList<>();

        for (final QueryComponent component : this.query.getComponents()) {
            if (component instanceof AnnotatorQueryComponent) {
                specifications.add(new AnnotatorQueryComponentSpecification(((AnnotatorQueryComponent) component).getAnnotator()));
            } else if (component instanceof InstanceidQueryComponent) {
                specifications.add(new InstanceidQueryComponentSpecification(((InstanceidQueryComponent) component).getInstanceid()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof PhaseQueryComponent) {
                specifications.add(new PhaseQueryComponentSpecification(((PhaseQueryComponent) component).getPhase()));
            } else if (component instanceof UUIDQueryComponent) {
                specifications.add(new UUIDQueryComponentSpecification(((UUIDQueryComponent) component).getUUID()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);
    }
    
}
