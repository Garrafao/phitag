package de.garrafao.phitag.application.instance.data;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.instance.IInstance;
import lombok.Getter;

@Getter
public class PagedInstanceDto {

    private final List<IInstanceDto> content;

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PagedInstanceDto() {
        this.content = null;
        this.page = 0;
        this.size = 0;
        this.totalElements = 0;
        this.totalPages = 0;
    }

    public PagedInstanceDto(List<IInstanceDto> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PagedInstanceDto from(final Page<IInstance> page) {
        return new PagedInstanceDto(
                page.getContent().stream().map(IInstanceDto::from).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
    
}
