package de.garrafao.phitag.domain.judgement.sentimentandchoice.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import lombok.Getter;

@Getter
public class SentimentAndChoiceJudgementPageBuilder {


    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public SentimentAndChoiceJudgementPageBuilder() {
    }

    public SentimentAndChoiceJudgementPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public SentimentAndChoiceJudgementPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public SentimentAndChoiceJudgementPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
}
