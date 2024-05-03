package de.garrafao.phitag.domain.report;

import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface ReportRepository {

    Report save(final Report report);

    Optional<Report> findById(final Integer id);

    Page<Report> findByQueryPaged(final Query query, final PageRequestWraper pagewraper);
    
}
