import UsageDto from "../dto/UsageDto";

import Pair from "../../../dummy/Pair";
import UsageId from "./UsageId";

export default class Usage {

    private readonly id: UsageId;

    private readonly context: string;
    private readonly indexTargetToken: Array<Pair<number, number>>;
    private readonly indexTargetSentence: Array<Pair<number, number>>;
    private readonly lemma: string;
    private readonly group: string;

    constructor(id: UsageId, context: string, indexTargetToken: Array<Pair<number, number>>, indexTargetSentence: Array<Pair<number, number>>, lemma: string, group: string = "") {
        this.id = id;
        this.context = context;
        this.indexTargetToken = indexTargetToken;
        this.indexTargetSentence = indexTargetSentence;
        this.lemma = lemma;
        this.group = group;
    }

    public getId(): UsageId {
        return this.id;
    }

    public getContext(): string {
        return this.context;
    }

    public getIndexTargetToken(): Array<Pair<number, number>> {
        return this.indexTargetToken;
    }

    public getIndexTargetTokenStart(index: number = 0): number {
        return this.indexTargetToken[index].left;
    }

    public getIndexTargetTokenEnd(index: number = 0): number {
        return this.indexTargetToken[index].right;
    }

    public getIndexTargetSentence(): Array<Pair<number, number>> {
        return this.indexTargetSentence;
    }

    public getIndexTargetSentenceStart(index: number = 0): number {
        return this.indexTargetSentence[index].left;
    }

    public getIndexTargetSentenceEnd(index: number = 0): number {
        return this.indexTargetSentence[index].right;
    }

    public getLemma(): string {
        return this.lemma;
    }

    public getGroup(): string {
        return this.group;
    }



    public static fromDto(dto: UsageDto): Usage {
        return new Usage(
            UsageId?.fromDto(dto?.id),
            dto?.context,
            dto?.indexTargetToken,
            dto?.indexTargetSentence,
            dto?.lemma,
            dto?.group
        );
    }


}