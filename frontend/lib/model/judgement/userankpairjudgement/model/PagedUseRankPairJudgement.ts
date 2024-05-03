import PagedGeneric from "../../../interfaces/PagedGeneric";
import UseRankPairJudgementDto from "../dto/UseRankPairJudgementDto";
import UseRankPairJudgement from "./UseRankPairJudgement";

export default class PagedUseRankPairJudgement implements PagedGeneric<UseRankPairJudgement> {
    readonly content: UseRankPairJudgement[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: UseRankPairJudgement[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): UseRankPairJudgement[] {
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

    static fromDto(dto: PagedGeneric<UseRankPairJudgementDto>): PagedUseRankPairJudgement {
        return new PagedUseRankPairJudgement(dto.content.map(UseRankPairJudgement.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    static empty(): PagedUseRankPairJudgement {
        return new PagedUseRankPairJudgement([], 0, 0, 0, 0);
    }

}