package de.garrafao.phitag.infrastructure.persistence.jpa.phitagdata.usage.query;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import de.garrafao.phitag.domain.phitagdata.usage.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UsageQueryJpa implements Specification<Usage> {

    private final Query query;

    public UsageQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Usage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Usage>> specifications = new ArrayList<>();

        for (final QueryComponent component: query.getComponents()) {
            if (component instanceof DataidQueryComponent) {
                specifications.add(new DataidQueryComponentSpecification(((DataidQueryComponent) component).getDataid()));
            } else if (component instanceof LemmaQueryComponent) {
                specifications.add(new LemmaQueryComponentSpecification(((LemmaQueryComponent) component).getLemma()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, criteriaQuery, criteriaBuilder);
    }
    
}
