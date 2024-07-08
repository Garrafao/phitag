package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.annotatorhistorytable;

import de.garrafao.phitag.domain.statistic.annotatorstathistory.AnnotatorHistoryTable;
import de.garrafao.phitag.domain.statistic.annotatorstathistory.AnnotatorHistoryTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnnotatorHistoryRepositoryBridge implements AnnotatorHistoryTableRepository {

    private final AnnotatorHistoryRepositoryJpa annotatorHistoryRepositoryJpa;

    @Autowired
    public AnnotatorHistoryRepositoryBridge(AnnotatorHistoryRepositoryJpa annotatorHistoryRepositoryJpa) {
        this.annotatorHistoryRepositoryJpa = annotatorHistoryRepositoryJpa;
    }

    @Override
    public List<AnnotatorHistoryTable> findByAnnotatornameAndOwnernameAndProjectnameAndPhasename(String annotatorname,
                                                                                                 String ownername, String projectname, String phasename) {
        return annotatorHistoryRepositoryJpa.findByAnnotatornameAndOwnernameAndProjectnameAndPhasename(annotatorname, ownername,
                projectname, phasename);
    }

    @Override
    public AnnotatorHistoryTable save(AnnotatorHistoryTable annotatorHistoryTable) {
        return annotatorHistoryRepositoryJpa.save(annotatorHistoryTable);
    }

}
