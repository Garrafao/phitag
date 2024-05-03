package de.garrafao.phitag.domain.joblisting.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class JoblistingPageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    JoblistingPageBuilder() {
    }

    public JoblistingPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public JoblistingPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public JoblistingPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
    
}
