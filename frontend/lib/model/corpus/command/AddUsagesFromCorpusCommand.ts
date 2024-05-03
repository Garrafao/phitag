export default class AddUsagesFromCorpusCommand {

    private readonly project: string;

    private readonly corpusTextIds: string[];
    private readonly lemma: string;
    private readonly pos: string;

    private readonly includeContext: boolean;
    private readonly normalizeContext: boolean;

    constructor(
        project: string,
        corpusTextIds: string[],
        lemma: string,
        pos: string,
        includeContext: boolean,
        normalizeContext: boolean
    ) {
        this.project = project;
        this.corpusTextIds = corpusTextIds;
        this.lemma = lemma;
        this.pos = pos;
        this.includeContext = includeContext;
        this.normalizeContext = normalizeContext;
    }

}