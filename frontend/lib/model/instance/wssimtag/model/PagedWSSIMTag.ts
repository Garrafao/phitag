import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import WSSIMTagDto from "../dto/WSSIMTagDto";
import WSSIMTag from "./WSSIMTag";

export default class PagedWSSIMTag implements PagedGeneric<WSSIMTag> {
    
        readonly content: WSSIMTag[];
    
        readonly page: number;
        readonly size: number;
        readonly totalElements: number;
        readonly totalPages: number;
    
        constructor(
            content: WSSIMTag[],
    
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
    
        public getContent(): WSSIMTag[] {
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
    
        public static fromDto(dto: PagedGenericDto<WSSIMTagDto>): PagedWSSIMTag {
            return new PagedWSSIMTag(
                dto.content.map(WSSIMTag.fromDto),
                dto.page,
                dto.size,
                dto.totalElements,
                dto.totalPages
            );
        }

        public static empty(): PagedWSSIMTag {
            return new PagedWSSIMTag([], 0, 0, 0, 0);
        }
    
}