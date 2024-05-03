import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import DictionaryDto from "../dto/DictionaryDto";
import Dictionary from "./Dictionary";

export default class PagedDictionary implements PagedGeneric<Dictionary> {
    
    readonly content: Dictionary[];
    
    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(
        content: Dictionary[],

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

    public static fromDto(dto: PagedGenericDto<DictionaryDto>): PagedDictionary {
        return new PagedDictionary(
            dto.content.map(Dictionary.fromDto),
            dto.page,
            dto.size,
            dto.totalElements,
            dto.totalPages
        );
    }

    public static empty(): PagedDictionary {
        return new PagedDictionary([], 0, 0, 0, 0);
    }

}