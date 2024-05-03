package de.garrafao.phitag.infrastructure.persistence.jpa.user.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.domain.user.query.EmailQueryComponent;
import de.garrafao.phitag.domain.user.query.FuzzyQueryComponent;
import de.garrafao.phitag.domain.user.query.IsBotQueryComponent;
import de.garrafao.phitag.domain.user.query.IsEnabledQueryComponent;
import de.garrafao.phitag.domain.user.query.LanguageQueryComponent;
import de.garrafao.phitag.domain.user.query.RoleQueryComponent;
import de.garrafao.phitag.domain.user.query.UsernameQueryComponent;
import de.garrafao.phitag.domain.user.query.VisibilityQueryComponent;
import de.garrafao.phitag.infrastructure.persistence.jpa.core.SpecificationCombiner;

public class UserQueryJpa implements Specification<User> {

    private final Query query;

    public UserQueryJpa(final Query query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Specification<User>> specifications = new ArrayList<>();

        for (QueryComponent component : this.query.getComponents()) {

            if (component instanceof FuzzyQueryComponent) {
                specifications.add(new FuzzyQueryComponentSpecification(((FuzzyQueryComponent) component).getValue()));
            } else if (component instanceof EmailQueryComponent) {
                specifications.add(new EmailQueryComponentSpecification(((EmailQueryComponent) component).getEmail()));
            } else if (component instanceof UsernameQueryComponent) {
                specifications.add(new UsernameQueryComponentSpecification(((UsernameQueryComponent) component).getUsername()));
            } else if (component instanceof IsBotQueryComponent) {
                specifications.add(new IsBotQueryComponentSpecification(((IsBotQueryComponent) component).getIsBot()));
            } else if (component instanceof IsEnabledQueryComponent) {
                specifications.add(new IsEnabledQueryComponentSpecification(((IsEnabledQueryComponent) component).getEnabled()));
            } else if (component instanceof LanguageQueryComponent) {
                specifications.add(new LanguageQueryComponentSpecification(((LanguageQueryComponent) component).getLanguage()));
            } else if (component instanceof RoleQueryComponent) {
                specifications.add(new RoleQueryComponentSpecification(((RoleQueryComponent) component).getRole()));
            } else if (component instanceof VisibilityQueryComponent) {
                specifications.add(new VisibilityQueryComponentSpecification(((VisibilityQueryComponent) component).getVisibility()));
            }
        }

        return SpecificationCombiner.and(specifications).toPredicate(root, criteriaQuery, criteriaBuilder);
    }
    
}
