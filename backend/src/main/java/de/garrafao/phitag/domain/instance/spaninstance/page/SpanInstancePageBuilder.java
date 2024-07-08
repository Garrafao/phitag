package de.garrafao.phitag.domain.instance.spaninstance.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class SpanInstancePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public SpanInstancePageBuilder() {
    }

    public SpanInstancePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public SpanInstancePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public SpanInstancePageBuilder withOrderBy(final String orderBy) {
        this.orderBy = orderBy;
        if (orderBy == null || orderBy.isEmpty()) {
            this.orderBy = "";
        }
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
    
}
