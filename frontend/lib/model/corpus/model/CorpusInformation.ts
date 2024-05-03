import CorpusInformationDto from "../dto/CorpusInformationDto";

export default class CorpusInformation {
    private readonly id: string;

    private readonly title: string;
    private readonly author: string;
    private readonly date: number;
    private readonly language: string;
    private readonly resource: string;

    private readonly corpusnameFull: string;
    private readonly corpusnameShort: string;

    constructor(
        id: string,

        title: string,
        author: string,
        date: number,
        language: string,
        resource: string,

        corpusnameFull: string,
        corpusnameShort: string
    ) {
        this.id = id;

        this.title = title;
        this.author = author;
        this.date = date;
        this.language = language;
        this.resource = resource;

        this.corpusnameFull = corpusnameFull;
        this.corpusnameShort = corpusnameShort;
    }

    public getId(): string {
        return this.id;
    }

    public getTitle(): string {
        return this.title;
    }

    public getAuthor(): string {
        return this.author;
    }

    public getDate(): number {
        return this.date;
    }

    public getLanguage(): string {
        return this.language;
    }

    public getResource(): string {
        return this.resource;
    }

    public getCorpusnameFull(): string {
        return this.corpusnameFull;
    }

    public getCorpusnameShort(): string {
        return this.corpusnameShort;
    }

    static fromDto(dto: CorpusInformationDto): CorpusInformation {
        return new CorpusInformation(
            dto.id,

            dto.title,
            dto.author,
            dto.date,
            dto.language,
            dto.resource,

            dto.corpusnameFull,
            dto.corpusnameShort
        );
    }

}