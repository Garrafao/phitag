package de.garrafao.phitag.domain.core;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.Getter;

@Getter
public class PageRequestWraper {

    private PageRequest pageRequest;

    public PageRequestWraper(int pagesize, int pagenumber) {
        this.pageRequest = PageRequest.of(pagenumber, pagesize);
    }

    public PageRequestWraper(int pagesize, int pagenumber, String orderBy) {
        if (orderBy == null || orderBy.isEmpty()) {
            this.pageRequest = PageRequest.of(pagenumber, pagesize);
            return;
        }
        this.pageRequest = PageRequest.of(pagenumber, pagesize, Sort.by(orderBy));
    }

}
