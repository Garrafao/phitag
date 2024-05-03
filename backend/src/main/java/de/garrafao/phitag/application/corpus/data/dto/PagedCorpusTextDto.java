package de.garrafao.phitag.application.corpus.data.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.corpus.Corpus;
import de.garrafao.phitag.domain.corpustext.CorpusText;
import lombok.Getter;

@Getter
public class PagedCorpusTextDto {

    private final List<CorpusTextDto> content;

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PagedCorpusTextDto(List<CorpusTextDto> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PagedCorpusTextDto from(final Page<CorpusText> page) {
        return new PagedCorpusTextDto(
                page.getContent().stream().map(CorpusTextDto::from).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
    
    public static PagedCorpusTextDto fromPagedCorpus(final Page<Corpus> page) {
        return new PagedCorpusTextDto(
                page.getContent().stream().map(entity -> CorpusTextDto.from(entity.getCorpusText())).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }

    public static PagedCorpusTextDto empty() {
        return new PagedCorpusTextDto(List.of(), 0, 0, 0, 0);
    }
}
