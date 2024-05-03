package de.garrafao.phitag.domain.report.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class ReportPageBuilder {
    
    private int pagesize;
    private int pagenumber;
    private String orderBy;

    ReportPageBuilder() {
    }

    public ReportPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public ReportPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public ReportPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
}
