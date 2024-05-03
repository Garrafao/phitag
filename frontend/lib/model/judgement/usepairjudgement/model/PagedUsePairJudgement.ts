import PagedGeneric from "../../../interfaces/PagedGeneric";
import UsePairJudgementDto from "../dto/UsePairJudgementDto";
import UsePairJudgement from "./UsePairJudgement";

export default class PagedUsePairJudgement implements PagedGeneric<UsePairJudgement> {
    readonly content: UsePairJudgement[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: UsePairJudgement[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): UsePairJudgement[] {
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

    static fromDto(dto: PagedGeneric<UsePairJudgementDto>): PagedUsePairJudgement {
        return new PagedUsePairJudgement(dto.content.map(UsePairJudgement.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    static empty(): PagedUsePairJudgement {
        return new PagedUsePairJudgement([], 0, 0, 0, 0);
    }

}