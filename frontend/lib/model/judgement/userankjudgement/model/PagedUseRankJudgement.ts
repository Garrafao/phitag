import PagedGeneric from "../../../interfaces/PagedGeneric";
import UseRankJudgementDto from "../dto/UseRankJudgementDto";
import UseRankJudgement from "./UseRankJudgement";

export default class PagedUseRankJudgement implements PagedGeneric<UseRankJudgement> {
    readonly content: UseRankJudgement[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: UseRankJudgement[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): UseRankJudgement[] {
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

    static fromDto(dto: PagedGeneric<UseRankJudgementDto>): PagedUseRankJudgement {
        return new PagedUseRankJudgement(dto.content.map(UseRankJudgement.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    static empty(): PagedUseRankJudgement {
        return new PagedUseRankJudgement([], 0, 0, 0, 0);
    }

}