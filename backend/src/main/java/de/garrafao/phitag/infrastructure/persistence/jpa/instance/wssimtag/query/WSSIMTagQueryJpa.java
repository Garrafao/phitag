package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssimtag.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import de.garrafao.phitag.domain.instance.wssimtag.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class WSSIMTagQueryJpa implements Specification<WSSIMTag> {

    private final Query query;

    public WSSIMTagQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<WSSIMTag> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<WSSIMTag>> specifications = new ArrayList<>();

        for (final QueryComponent component : this.query.getComponents()) {
            if (component instanceof TagidQueryComponent) {
                specifications.add(new TagidQueryComponentSpecification(
                        ((TagidQueryComponent) component).getTagid()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications
                        .add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof PhaseQueryComponent) {
                specifications.add(new PhaseQueryComponentSpecification(((PhaseQueryComponent) component).getPhase()));
            } else if (component instanceof LemmaQueryComponent) {
                specifications.add(new LemmaQueryComponentSpecification(((LemmaQueryComponent) component).getLemma()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);
    }

}
