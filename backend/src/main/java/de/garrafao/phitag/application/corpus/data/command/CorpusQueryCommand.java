package de.garrafao.phitag.application.corpus.data.command;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class CorpusQueryCommand {

    // Page Information
    private final Integer page;
    private final Integer size;

    // Lemma
    private final String lemma;
    private final String pos;

    // Corpus
    private final String corpus;

    // Context
    private Boolean context;

    // Time period
    private final Integer from;
    private final Integer to;

    public CorpusQueryCommand(
            Integer page, Integer size,
            String lemma, String pos,
            String corpus,
            Boolean context,
            Integer from, Integer to) {
        Validate.inclusiveBetween(0, 50, size, "size must be between 0 and 50");

        this.page = page;
        this.size = size;

        this.lemma = lemma;
        this.pos = pos;

        this.corpus = corpus;

        this.context = context;

        this.from = from;
        this.to = to;
    }

}
