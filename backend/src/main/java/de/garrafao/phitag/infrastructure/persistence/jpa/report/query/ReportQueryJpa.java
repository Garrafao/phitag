package de.garrafao.phitag.infrastructure.persistence.jpa.report.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.report.Report;
import de.garrafao.phitag.domain.report.query.StatusQueryComponent;
import de.garrafao.phitag.domain.report.query.UserQueryComponent;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class ReportQueryJpa implements Specification<Report> {

    private final Query query;

    public ReportQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Report>> specifications = new ArrayList<>();

        for (QueryComponent component : this.query.getComponents()) {

            if (component instanceof UserQueryComponent) {
                specifications.add(new UserQueryComponentSpecification(((UserQueryComponent) component).getUsername()));
            } else if (component instanceof StatusQueryComponent) {
                specifications
                        .add(new StatusQueryComponentSpecification(((StatusQueryComponent) component).getStatus()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);

    }
}
