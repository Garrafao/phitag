package de.garrafao.phitag.domain.judgement.userankpairjudgement.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import lombok.Getter;

@Getter
public class UseRankPairJudgementPageBuilder {


    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UseRankPairJudgementPageBuilder() {
    }

    public UseRankPairJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UseRankPairJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UseRankPairJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
