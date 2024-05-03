package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userankrelative.query;

import de.garrafao.phitag.domain.annotationprocessinformation.query.PhaseQueryComponent;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.judgement.common.query.AnnotatorQueryComponent;
import de.garrafao.phitag.domain.judgement.common.query.OwnerQueryComponent;
import de.garrafao.phitag.domain.judgement.common.query.ProjectQueryComponent;
import de.garrafao.phitag.domain.judgement.common.query.UUIDQueryComponent;
import de.garrafao.phitag.domain.tutorial.userankrelative.UseRankRelativeTutorialJudgement;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UseRankRelativeTutorialJudgementQueryJpa implements Specification<UseRankRelativeTutorialJudgement> {

    private final Query query;

    public UseRankRelativeTutorialJudgementQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<UseRankRelativeTutorialJudgement> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<UseRankRelativeTutorialJudgement>> specifications = new ArrayList<>();

        for (final QueryComponent component : query.getComponents()) {
            if (component instanceof AnnotatorQueryComponent) {
                specifications.add(new AnnotatorQueryComponentSpecification(((AnnotatorQueryComponent) component).getAnnotator()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof PhaseQueryComponent) {
                specifications.add(new PhaseQueryComponentSpecification(((PhaseQueryComponent) component).getPhase()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof UUIDQueryComponent) {
                specifications.add(new UUIDQueryComponentSpecification(((UUIDQueryComponent) component).getUUID()));
            }
        }
    
        return SpecificationCombiner.and(specifications).toPredicate(root, criteriaQuery, criteriaBuilder);
    }
    
}
