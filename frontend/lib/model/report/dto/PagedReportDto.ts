import ReportDto from "./ReportDto";

export default interface PagedReportDto {
    readonly content: ReportDto[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;
}

