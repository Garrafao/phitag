export default interface PagedGeneric<T> {

    readonly content: T[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;
}