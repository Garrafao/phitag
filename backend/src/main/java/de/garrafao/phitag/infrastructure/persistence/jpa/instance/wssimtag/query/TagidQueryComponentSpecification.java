package de.garrafao.phitag.infrastructure.persistence.jpa.instance.wssimtag.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import lombok.Getter;

@Getter
public class TagidQueryComponentSpecification implements Specification<WSSIMTag> {

    private final String tagid;

    public TagidQueryComponentSpecification(final String tagid) {
        this.tagid = tagid;
    }

    @Override
    public Predicate toPredicate(Root<WSSIMTag> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("id").get("tagid"), tagid);
    }
    
}
