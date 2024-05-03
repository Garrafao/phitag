package de.garrafao.phitag.domain.instance.usepairinstance.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class UsePairInstancePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UsePairInstancePageBuilder() {
    }

    public UsePairInstancePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UsePairInstancePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UsePairInstancePageBuilder withOrderBy(final String orderBy) {
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
