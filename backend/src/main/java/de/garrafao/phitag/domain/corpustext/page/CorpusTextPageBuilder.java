package de.garrafao.phitag.domain.corpustext.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class CorpusTextPageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    CorpusTextPageBuilder() {
    }

    public CorpusTextPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public CorpusTextPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public CorpusTextPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }

    
    
}
