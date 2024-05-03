package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.annotatorstatistic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.statistic.annotatorstatistic.AnnotatorStatistic;
import de.garrafao.phitag.domain.statistic.annotatorstatistic.AnnotatorStatisticRepository;

@Repository
public class AnnotatorStaticRepositoryBridge implements AnnotatorStatisticRepository {

    private final AnnotatorStaticRepositoryJpa annotatorStaticRepositoryJpa;

    @Autowired
    public AnnotatorStaticRepositoryBridge(AnnotatorStaticRepositoryJpa annotatorStaticRepositoryJpa) {
        this.annotatorStaticRepositoryJpa = annotatorStaticRepositoryJpa;
    }

    @Override
    public Optional<AnnotatorStatistic> findByAnnotatornameAndOwnernameAndProjectname(String annotatorname,
            String ownername, String projectname) {
        return annotatorStaticRepositoryJpa.findByAnnotatornameAndOwnernameAndProjectname(annotatorname, ownername,
                projectname);
    }

    @Override
    public AnnotatorStatistic save(AnnotatorStatistic annotatorStatistic) {
        return annotatorStaticRepositoryJpa.save(annotatorStatistic);
    }

}
