package de.garrafao.phitag.domain.tutorial.wssim.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class WSSIMTutorialJudgementPageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public WSSIMTutorialJudgementPageBuilder() {
    }

    public WSSIMTutorialJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public WSSIMTutorialJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public WSSIMTutorialJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }

}
