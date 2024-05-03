package de.garrafao.phitag.domain.instance.sentimentandchoice.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class SentimentAndChoicePageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    public SentimentAndChoicePageBuilder() {
    }

    public SentimentAndChoicePageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public SentimentAndChoicePageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public SentimentAndChoicePageBuilder withOrderBy(final String orderBy) {
        this.orderBy = orderBy;
        if (orderBy == null || orderBy.isEmpty()) {
            this.orderBy = "";
        }
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }
    
    
}
