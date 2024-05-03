package de.garrafao.phitag.domain.judgement.usepairjudgement.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import lombok.Getter;

@Getter
public class UsePairJudgementPageBuilder {


    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UsePairJudgementPageBuilder() {
    }

    public UsePairJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UsePairJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UsePairJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
