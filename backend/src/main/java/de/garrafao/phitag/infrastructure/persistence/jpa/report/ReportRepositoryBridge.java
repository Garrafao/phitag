package de.garrafao.phitag.infrastructure.persistence.jpa.report;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.report.Report;
import de.garrafao.phitag.domain.report.ReportRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.report.query.ReportQueryJpa;

@Repository
public class ReportRepositoryBridge implements ReportRepository {

    private final ReportRepositoryJpa reportRepositoryJpa;

    @Autowired
    public ReportRepositoryBridge(ReportRepositoryJpa reportRepositoryJpa) {
        this.reportRepositoryJpa = reportRepositoryJpa;
    }

    @Override
    public Report save(Report report) {
        return this.reportRepositoryJpa.save(report);
    }

    @Override
    public Optional<Report> findById(Integer id) {
        return this.reportRepositoryJpa.findById(id);
    }

    @Override
    public Page<Report> findByQueryPaged(Query query, PageRequestWraper pagewraper) {
        return this.reportRepositoryJpa.findAll(new ReportQueryJpa(query), pagewraper.getPageRequest());
    }
    
    
}
