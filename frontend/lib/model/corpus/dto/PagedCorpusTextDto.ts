import CorpusTextDto from './CorpusTextDto';

export default interface PagedCorpusTextDto {

    readonly content: CorpusTextDto[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;
}