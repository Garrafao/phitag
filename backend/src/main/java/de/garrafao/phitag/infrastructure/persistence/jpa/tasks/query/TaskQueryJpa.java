package de.garrafao.phitag.infrastructure.persistence.jpa.tasks.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.tasks.Task;
import de.garrafao.phitag.domain.tasks.query.*;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class TaskQueryJpa implements Specification<Task> {

    private final Query query;

    public TaskQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Specification<Task>> specifications = new ArrayList<>();

        for (QueryComponent component : this.query.getComponents()) {
            
            if (component instanceof BotQueryComponent) {
                specifications.add(new BotQueryComponentSpecification(((BotQueryComponent) component).getBotname()));
            } else if (component instanceof StatusQueryComponent) {
                specifications.add(new StatusQueryComponentSpecification(((StatusQueryComponent) component).getStatus()));
            } else if (component instanceof OwnerQueryComponent) {
                specifications.add(new OwnerQueryComponentSpecification(((OwnerQueryComponent) component).getOwner()));
            } else if (component instanceof ProjectQueryComponent) {
                specifications.add(new ProjectQueryComponentSpecification(((ProjectQueryComponent) component).getProject()));
            } else if (component instanceof PhaseQueryComponent) {
                specifications.add(new PhaseQueryComponentSpecification(((PhaseQueryComponent) component).getPhase()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, query, criteriaBuilder);
        
    }

}
