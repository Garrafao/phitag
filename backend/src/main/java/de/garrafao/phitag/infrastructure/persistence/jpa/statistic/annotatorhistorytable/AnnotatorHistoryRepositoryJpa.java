package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.annotatorhistorytable;

import de.garrafao.phitag.domain.statistic.annotatorstathistory.AnnotatorHistoryTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnotatorHistoryRepositoryJpa extends JpaRepository<AnnotatorHistoryTable, String> {

    List<AnnotatorHistoryTable> findByAnnotatornameAndOwnernameAndProjectnameAndPhasename(final String annotatorname,
                                                                                          final String ownername, final String projectname, final String phasename);
}
