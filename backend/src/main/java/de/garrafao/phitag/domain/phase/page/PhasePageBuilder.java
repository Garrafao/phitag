package de.garrafao.phitag.domain.phase.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class PhasePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    PhasePageBuilder() {
    }

    public PhasePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public PhasePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public PhasePageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
