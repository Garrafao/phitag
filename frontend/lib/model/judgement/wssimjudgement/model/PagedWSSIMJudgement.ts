import PagedGeneric from "../../../interfaces/PagedGeneric";
import WSSIMJudgementDto from "../dto/WSSIMJudgementDto";
import WSSIMJudgement from "./WSSIMJudgement";

export default class PagedWSSIMJudgement implements PagedGeneric<WSSIMJudgement> {
    readonly content: WSSIMJudgement[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: WSSIMJudgement[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): WSSIMJudgement[] {
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

    static fromDto(dto: PagedGeneric<WSSIMJudgementDto>): PagedWSSIMJudgement {
        return new PagedWSSIMJudgement(dto.content.map(WSSIMJudgement.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    static empty(): PagedWSSIMJudgement {
        return new PagedWSSIMJudgement([], 0, 0, 0, 0);
    }

}