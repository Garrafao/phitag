package de.garrafao.phitag.infrastructure.persistence.jpa.annotator.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.language.Language;
import de.garrafao.phitag.domain.user.User;

public class LanguageQueryComponentSpecification implements Specification<Annotator> {

    private final String language;

    public LanguageQueryComponentSpecification(final String language) {
        this.language = language;
    }

    @Override
    public Predicate toPredicate(Root<Annotator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate empty = criteriaBuilder.isEmpty(root.get("user").get("languages"));

        Join<Annotator, User> userJoin = root.join("user");
        Join<User, Language> joinLanguage = userJoin.join("languages", JoinType.LEFT);

        Predicate inSet = criteriaBuilder.equal(joinLanguage.get("name"), language);

        return criteriaBuilder.or(empty, inSet);
    }

}
