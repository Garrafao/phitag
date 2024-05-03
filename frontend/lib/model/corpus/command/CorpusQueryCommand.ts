export default class CorpusQueryCommand {
    readonly page: number;
    readonly size: number;

    readonly lemma: string;
    readonly pos: string;

    readonly context: boolean;

    readonly from: number;
    readonly to: number;

    constructor(
        page: number,
        pageSize: number,

        lemma: string,
        pos: string,

        context: boolean,

        from: number,
        to: number
    ) {
        this.page = page;
        this.size = pageSize;

        this.lemma = lemma;
        this.pos = pos;

        this.context = context;

        this.from = from;
        this.to = to;
    }
}