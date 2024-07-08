package de.garrafao.phitag.domain.instance.lexsub.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class LexSubInstancePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public LexSubInstancePageBuilder() {
    }

    public LexSubInstancePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public LexSubInstancePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public LexSubInstancePageBuilder withOrderBy(final String orderBy) {
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
