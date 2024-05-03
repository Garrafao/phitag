import PagedReportDto from "../dto/PagedReportDto";
import Report from "./Report";

export default class PagedReport {

    private readonly content: Report[];

    private readonly page: number;
    private readonly size: number;
    private readonly totalElements: number;
    private readonly totalPages: number;

    constructor(
        content: Report[],

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

    public getContent(): Report[] {
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

    public static fromDto(dto: PagedReportDto): PagedReport {
        return new PagedReport(
            dto.content.map(Report.fromDto),
            dto.page,
            dto.size,
            dto.totalElements,
            dto.totalPages
        );
    }
}