package de.garrafao.phitag.infrastructure.persistence.jpa.report;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.report.Report;

public interface ReportRepositoryJpa extends JpaRepository<Report, Integer>, JpaSpecificationExecutor<Report> {

    public Optional<Report> findById(final Integer id);

}
