package de.garrafao.phitag.domain.tutorial.usepair.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import lombok.Getter;

@Getter
public class UsePairTutorialJudgementPageBuilder {


    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UsePairTutorialJudgementPageBuilder() {
    }

    public UsePairTutorialJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UsePairTutorialJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UsePairTutorialJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
