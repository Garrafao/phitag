package de.garrafao.phitag.application.report.data.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.report.Report;
import lombok.Getter;

@Getter
public class PagedReportDto {

    private final List<ReportDto> content;

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    
    public PagedReportDto(List<ReportDto> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PagedReportDto empty() {
        return new PagedReportDto(List.of(), 0, 0, 0, 0);
    }

    public static PagedReportDto from(final Page<Report> page) {
        return new PagedReportDto(
                page.getContent().stream().map(ReportDto::from).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
