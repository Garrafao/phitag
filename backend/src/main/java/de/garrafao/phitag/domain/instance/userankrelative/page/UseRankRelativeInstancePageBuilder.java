package de.garrafao.phitag.domain.instance.userankrelative.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class UseRankRelativeInstancePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UseRankRelativeInstancePageBuilder() {
    }

    public UseRankRelativeInstancePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UseRankRelativeInstancePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UseRankRelativeInstancePageBuilder withOrderBy(final String orderBy) {
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
