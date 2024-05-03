package de.garrafao.phitag.application.dictionary.dictionary.data;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;
import lombok.Getter;

@Getter
public class PagedDictionaryDto {

    private final List<DictionaryDto> content;

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    private PagedDictionaryDto(final List<DictionaryDto> content, final int page, final int size,
            final long totalElements, final int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PagedDictionaryDto from(final Page<Dictionary> page) {
        return new PagedDictionaryDto(
                page.getContent().stream().map(DictionaryDto::from).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }

}
