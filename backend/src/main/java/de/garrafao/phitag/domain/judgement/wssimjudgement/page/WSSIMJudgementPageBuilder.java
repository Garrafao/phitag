package de.garrafao.phitag.domain.judgement.wssimjudgement.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class WSSIMJudgementPageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public WSSIMJudgementPageBuilder() {
    }

    public WSSIMJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public WSSIMJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public WSSIMJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }

}
