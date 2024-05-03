import PagedGeneric from "../../../interfaces/PagedGeneric";
import SentimentJudgementDto from "../dto/SentimentJudgementDto";
import SentimentJudgement from "./SentimentJudgement";
export default class PagedSentimentJudgement implements PagedGeneric<SentimentJudgement> {

    readonly content: SentimentJudgement[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: SentimentJudgement[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): SentimentJudgement[] {
        return this.content;
    }

    public getPage(): number {
        return this.page;
    }

    public getSize(): number {
        return this.size;
    }

    public getTotalElements(): number {
        return this.totalElements;
    }

    public getTotalPages(): number {
        return this.totalPages;
    }

    static fromDto(dto: PagedGeneric<SentimentJudgementDto>): PagedSentimentJudgement {
        return new PagedSentimentJudgement(dto.content.map(SentimentJudgement.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    static empty(): PagedSentimentJudgement {
        return new PagedSentimentJudgement([], 0, 0, 0, 0);
    }
}