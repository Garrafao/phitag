package de.garrafao.phitag.domain.user.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class UserPageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    UserPageBuilder() {
    }

    public UserPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UserPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UserPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
