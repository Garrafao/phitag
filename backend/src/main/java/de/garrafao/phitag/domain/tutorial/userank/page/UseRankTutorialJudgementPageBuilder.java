package de.garrafao.phitag.domain.tutorial.userank.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import lombok.Getter;

@Getter
public class UseRankTutorialJudgementPageBuilder {


    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UseRankTutorialJudgementPageBuilder() {
    }

    public UseRankTutorialJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UseRankTutorialJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UseRankTutorialJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
