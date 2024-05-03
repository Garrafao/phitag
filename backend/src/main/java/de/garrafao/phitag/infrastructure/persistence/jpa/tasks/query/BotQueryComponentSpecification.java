package de.garrafao.phitag.infrastructure.persistence.jpa.tasks.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.tasks.Task;

public class BotQueryComponentSpecification implements Specification<Task> {

    private final String botname;

    public BotQueryComponentSpecification(final String botname) {
        this.botname = botname;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("bot").get("username"), botname);
    }
    
}
