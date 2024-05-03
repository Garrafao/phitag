import PagedGeneric from "../../../interfaces/PagedGeneric";
import LexSubJudgementDto from "../dto/LexSubJudgementDto";
import LexSubJudgement from "./LexSubJudgement";

export default class PagedLexSubJudgement implements PagedGeneric<LexSubJudgement> {

    readonly content: LexSubJudgement[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: LexSubJudgement[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): LexSubJudgement[] {
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

    static fromDto(dto: PagedGeneric<LexSubJudgementDto>): PagedLexSubJudgement {
        return new PagedLexSubJudgement(dto.content.map(LexSubJudgement.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    static empty(): PagedLexSubJudgement {
        return new PagedLexSubJudgement([], 0, 0, 0, 0);
    }
}