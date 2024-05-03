package de.garrafao.phitag.domain.instance.wssiminstance.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class WSSIMInstancePageBuilder {
 
    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public WSSIMInstancePageBuilder() {
    }

    public WSSIMInstancePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public WSSIMInstancePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public WSSIMInstancePageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
    
}
