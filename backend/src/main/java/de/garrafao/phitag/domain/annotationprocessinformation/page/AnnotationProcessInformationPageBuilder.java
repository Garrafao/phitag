package de.garrafao.phitag.domain.annotationprocessinformation.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class AnnotationProcessInformationPageBuilder {
    private int pagesize;
    private int pagenumber;
    private String orderBy;

    AnnotationProcessInformationPageBuilder() {
    }

    public AnnotationProcessInformationPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public AnnotationProcessInformationPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public AnnotationProcessInformationPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
}
