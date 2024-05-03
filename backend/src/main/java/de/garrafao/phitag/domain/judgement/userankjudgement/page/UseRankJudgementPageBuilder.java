package de.garrafao.phitag.domain.judgement.userankjudgement.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import lombok.Getter;

@Getter
public class UseRankJudgementPageBuilder {


    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UseRankJudgementPageBuilder() {
    }

    public UseRankJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UseRankJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UseRankJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
