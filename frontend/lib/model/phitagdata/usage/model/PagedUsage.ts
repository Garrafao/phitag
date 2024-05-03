import PagedUsageDto from "../dto/PagedUsageDto";
import Usage from "./Usage";

export default class PagedUsage {

    private readonly content: Usage[];

    private readonly page: number;
    private readonly size: number;
    private readonly totalElements: number;
    private readonly totalPages: number;

    constructor(
        content: Usage[],
        
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

    public getContent(): Usage[] {
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

    public static fromDto(dto: PagedUsageDto): PagedUsage {
        return new PagedUsage(
            dto.content.map(Usage.fromDto),
            dto.page,
            dto.size,
            dto.totalElements,
            dto.totalPages
        );
    }
    
}