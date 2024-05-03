package de.garrafao.phitag.infrastructure.persistence.jpa.instance.sentimentandchoiceannotation.query;

import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PhaseQueryComponentSpecification implements Specification<SentimentAndChoiceInstance> {

    private final String phase;

    public PhaseQueryComponentSpecification(final String phase) {
        this.phase = phase;
    }

    @Override
    public Predicate toPredicate(Root<SentimentAndChoiceInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("phaseid").get("name"), phase);
    }
    
}
