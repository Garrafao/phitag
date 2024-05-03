package de.garrafao.phitag.infrastructure.persistence.jpa.core;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationCombiner<T> {

    public SpecificationCombiner() {
    }

    public static <T> Specification<T> and(List<Specification<T>> specifications) {
        Validate.notNull(specifications, "Specification list must not be null");
        if (specifications.isEmpty()) {
            return new EmptyQuerySpecification<>();
        }

        Specification<T> result = Specification.where(null);
        for (Specification<T> specification : specifications) {
            result = result.and(specification);
        }

        return result;

    }

    public static <T> Specification<T> or(List<Specification<T>> specifications) {
        Validate.notNull(specifications, "Specification list must not be null");
        if (specifications.isEmpty()) {
            return new EmptyQuerySpecification<>();
        }

        Specification<T> result = Specification.where(null);
        for (Specification<T> specification : specifications) {
            result = result.or(specification);
        }

        return result;

    }

}
