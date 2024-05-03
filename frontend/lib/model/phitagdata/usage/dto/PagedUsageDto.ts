import UsageDto from "./UsageDto";

export default interface PagedUsageDto {

    readonly content: UsageDto[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;
}