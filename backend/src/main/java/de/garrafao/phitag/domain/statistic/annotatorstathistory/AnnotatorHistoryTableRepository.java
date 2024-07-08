package de.garrafao.phitag.domain.statistic.annotatorstathistory;

import java.util.List;

public interface AnnotatorHistoryTableRepository {

    public List<AnnotatorHistoryTable> findByAnnotatornameAndOwnernameAndProjectnameAndPhasename(final String annotatorname,
                                                                                                 final String ownername, final String projectname,
                                                                                                 final String phasename);

    public AnnotatorHistoryTable save(final AnnotatorHistoryTable annotatorHistoryTable);
}
