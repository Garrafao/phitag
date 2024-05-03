import CorpusTextSmallDto from "../dto/CorpusTextSmallDto";

export default class CorpusTextSmall {
    private readonly id: string;

    private readonly text: string;
    private readonly orthography: string;

    constructor(id: string, text: string, orthography: string) {
        this.id = id;

        this.text = text;
        this.orthography = orthography;
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
    
    static fromDto(dto: CorpusTextSmallDto): CorpusTextSmall {
        return new CorpusTextSmall(dto.id, dto.text, dto.orthography);
    }

}