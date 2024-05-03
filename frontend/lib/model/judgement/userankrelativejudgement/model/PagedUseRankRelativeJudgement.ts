import PagedGeneric from "../../../interfaces/PagedGeneric";
import UseRankRelativeJudgementDto from "../dto/UseRankRelativeJudgementDto";
import UseRankRelativeJudgement from "./UseRankRelativeJudgement";

export default class PagedUseRankRelativeJudgement implements PagedGeneric<UseRankRelativeJudgement> {
    readonly content: UseRankRelativeJudgement[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: UseRankRelativeJudgement[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): UseRankRelativeJudgement[] {
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

    static fromDto(dto: PagedGeneric<UseRankRelativeJudgementDto>): PagedUseRankRelativeJudgement {
        return new PagedUseRankRelativeJudgement(dto.content.map(UseRankRelativeJudgement.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    static empty(): PagedUseRankRelativeJudgement {
        return new PagedUseRankRelativeJudgement([], 0, 0, 0, 0);
    }

}