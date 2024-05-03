package de.garrafao.phitag.domain.tutorial.userankpair.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import lombok.Getter;

@Getter
public class UseRankPairTutorialJudgementPageBuilder {


    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UseRankPairTutorialJudgementPageBuilder() {
    }

    public UseRankPairTutorialJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UseRankPairTutorialJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UseRankPairTutorialJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
