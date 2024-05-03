package de.garrafao.phitag.domain.project.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class ProjectPageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    ProjectPageBuilder() {
    }

    public ProjectPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public ProjectPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public ProjectPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
