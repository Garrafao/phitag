package de.garrafao.phitag.domain.tutorial.userankrelative.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import lombok.Getter;

@Getter
public class UseRankRelativeTutorialJudgementPageBuilder {


    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public UseRankRelativeTutorialJudgementPageBuilder() {
    }

    public UseRankRelativeTutorialJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public UseRankRelativeTutorialJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public UseRankRelativeTutorialJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
