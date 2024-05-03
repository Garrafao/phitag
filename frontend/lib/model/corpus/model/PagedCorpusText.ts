import CorpusText from "./CorpusText";
import PagedCorpusTextDto from "../dto/PagedCorpusTextDto";

export default class PagedCorpusText {

    private readonly content: CorpusText[];

    private readonly page: number;
    private readonly size: number;
    private readonly totalElements: number;
    private readonly totalPages: number;

    constructor(
        content: CorpusText[],
        
        page: number,
        size: number,
        totalElements: number,
        totalPages: number
    ) {
        this.content = content;

        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public getContent(): CorpusText[] {
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

    public static fromDto(dto: PagedCorpusTextDto): PagedCorpusText {
        return new PagedCorpusText(
            dto.content.map(CorpusText.fromDto),
            dto.page,
            dto.size,
            dto.totalElements,
            dto.totalPages
        );
    }
    
}