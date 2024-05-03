package de.garrafao.phitag.application.judgement.data;

import java.util.List;

import lombok.Getter;

@Getter
public class PagedJudgementDto {

    private final List<IJudgementDto> content;

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PagedJudgementDto(List<IJudgementDto> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
    
    
}
