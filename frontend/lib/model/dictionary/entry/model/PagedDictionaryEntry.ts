import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import DictionaryEntryDto from "../dto/DictionaryEntryDto";
import DictionaryEntry from "./DictionaryEntry";

export default class PagedDictionaryEntry implements PagedGeneric<DictionaryEntry> {

    readonly content: DictionaryEntry[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(content: DictionaryEntry[], page: number, size: number, totalElements: number, totalPages: number) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static fromDto(dto: PagedGenericDto<DictionaryEntryDto>): PagedDictionaryEntry {
        return new PagedDictionaryEntry(dto.content.map(DictionaryEntry.fromDto), dto.page, dto.size, dto.totalElements, dto.totalPages);
    }

    public static empty(): PagedDictionaryEntry {
        return new PagedDictionaryEntry([], 0, 0, 0, 0);
    }

}