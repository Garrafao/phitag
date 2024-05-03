package de.garrafao.phitag.domain.instance.userankpairinstances.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class UseRankPairInstancePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UseRankPairInstancePageBuilder() {
    }

    public UseRankPairInstancePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UseRankPairInstancePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UseRankPairInstancePageBuilder withOrderBy(final String orderBy) {
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
