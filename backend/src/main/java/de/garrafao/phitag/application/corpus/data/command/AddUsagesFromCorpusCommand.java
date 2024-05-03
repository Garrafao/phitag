package de.garrafao.phitag.application.corpus.data.command;

import java.util.List;

import lombok.Getter;

@Getter
public class AddUsagesFromCorpusCommand {

    private final String project;

    private final List<String> corpusTextIds;
    private final String lemma;
    private final String pos;

    private final boolean includeContext;
    private final boolean normalizeContext;

    public AddUsagesFromCorpusCommand(
            final String project,
            final List<String> corpusTextIds,
            final String lemma,
            final String pos,
            boolean includeContext,
            boolean normalizeContext) {
        this.project = project;

        this.corpusTextIds = corpusTextIds;
        this.lemma = lemma;
        this.pos = pos;

        this.includeContext = includeContext;
        this.normalizeContext = normalizeContext;
    }

}
