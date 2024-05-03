package de.garrafao.phitag.domain.phitagdata.usage.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class UsagePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UsagePageBuilder() {
    }

    public UsagePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UsagePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UsagePageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }

}
