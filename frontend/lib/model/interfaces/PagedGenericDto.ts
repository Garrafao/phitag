export default interface PagedGenericDto<T> {

    readonly content: T[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;
}