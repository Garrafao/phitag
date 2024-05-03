import CorpusTextDto from "../dto/CorpusTextDto";
import CorpusInformation from "./CorpusInformation";
import CorpusTextSmall from "./CorpusTextSmall";

export default class CorpusText {

    private readonly id: string;

    private readonly text: string;
    private readonly orthography: string;

    private readonly previous: CorpusTextSmall;
    private readonly next: CorpusTextSmall;

    private readonly information: CorpusInformation;

    constructor(
        id: string,

        text: string,
        orthography: string,

        previous: CorpusTextSmall,
        next: CorpusTextSmall,

        information: CorpusInformation
    ) {
        this.id = id;

        this.text = text;
        this.orthography = orthography;

        this.previous = previous;
        this.next = next;

        this.information = information;
    }

    public getId(): string {
        return this.id;
    }

    public getText(): string {
        return this.text;
    }

    public getOrthography(): string {
        return this.orthography;
    }

    public getPrevious(): CorpusTextSmall {
        return this.previous;
    }

    public getNext(): CorpusTextSmall {
        return this.next;
    }

    public getInformation(): CorpusInformation {
        return this.information;
    }

    static fromDto(dto: CorpusTextDto): CorpusText {
        return new CorpusText(
            dto.id,

            dto.text,
            dto.orthography,

            CorpusTextSmall.fromDto(dto.previous),
            CorpusTextSmall.fromDto(dto.next),

            CorpusInformation.fromDto(dto.corpusInformation)
        );
    }

}