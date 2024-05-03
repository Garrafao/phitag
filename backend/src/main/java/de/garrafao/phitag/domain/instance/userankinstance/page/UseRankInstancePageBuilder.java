package de.garrafao.phitag.domain.instance.userankinstance.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class UseRankInstancePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UseRankInstancePageBuilder() {
    }

    public UseRankInstancePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UseRankInstancePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UseRankInstancePageBuilder withOrderBy(final String orderBy) {
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
