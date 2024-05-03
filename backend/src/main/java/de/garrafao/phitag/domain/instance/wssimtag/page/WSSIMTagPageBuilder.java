package de.garrafao.phitag.domain.instance.wssimtag.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class WSSIMTagPageBuilder {
    
    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public WSSIMTagPageBuilder() {
    }

    public WSSIMTagPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public WSSIMTagPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public WSSIMTagPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
    
}
