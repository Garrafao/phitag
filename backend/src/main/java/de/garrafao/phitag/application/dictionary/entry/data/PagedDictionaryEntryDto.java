package de.garrafao.phitag.application.dictionary.entry.data;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import lombok.Getter;

@Getter
public class PagedDictionaryEntryDto {
    
    private final List<DictionaryEntryDto> content;

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    private PagedDictionaryEntryDto(final List<DictionaryEntryDto> content, final int page, final int size,
            final long totalElements, final int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PagedDictionaryEntryDto from(final Page<DictionaryEntry> page) {
        return new PagedDictionaryEntryDto(
                page.getContent().stream().map(DictionaryEntryDto::from).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }

}
