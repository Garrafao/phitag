package de.garrafao.phitag.application.phitagdata.usage.data;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import lombok.Getter;

@Getter
public class PagedUsageDto {

    private final List<UsageDto> content;

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PagedUsageDto(List<UsageDto> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PagedUsageDto from(final Page<Usage> page) {
        return new PagedUsageDto(
                page.getContent().stream().map(UsageDto::from).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }

    
}
