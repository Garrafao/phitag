import PagedGeneric from "../../../interfaces/PagedGeneric";
import SpanJudgementDto from "../dto/SpanJudgementDto";
import SpanJudgement from "./SpanJudgement";

export default class PagedSpanJudgement implements PagedGeneric<SpanJudgement> {

    readonly content: SpanJudgement[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: SpanJudgement[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): SpanJudgement[] {
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

    static fromDto(dto: PagedGeneric<SpanJudgementDto>): PagedSpanJudgement {
        return new PagedSpanJudgement(dto.content.map(SpanJudgement.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    static empty(): PagedSpanJudgement {
        return new PagedSpanJudgement([], 0, 0, 0, 0);
    }
}