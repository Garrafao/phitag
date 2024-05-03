package de.garrafao.phitag.domain.corpus.page;

import de.garrafao.phitag.domain.core.PageRequestWraper;

public class CorpusPageBuilder {

    private int pagesize;
    private int pagenumber;
    private String orderBy;

    CorpusPageBuilder() {
    }

    public CorpusPageBuilder withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    public CorpusPageBuilder withPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
        return this;
    }

    public CorpusPageBuilder withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public PageRequestWraper build() {
        return new PageRequestWraper(pagesize, pagenumber, orderBy);
    }

    
    
}

