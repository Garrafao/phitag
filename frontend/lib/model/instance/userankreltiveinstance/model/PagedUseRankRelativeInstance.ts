import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import UseRankInstanceDto from "../dto/UseRankRelativeInstanceDto";
import UseRankRelativeInstance from "./UseRankRelativeInstance";


export default class PagedUseRankRelativeInstance implements PagedGeneric<UseRankRelativeInstance> {
    
        readonly content: UseRankRelativeInstance[];
    
        readonly page: number;
        readonly size: number;
        readonly totalElements: number;
        readonly totalPages: number;
    
        constructor(
            content: UseRankRelativeInstance[],
            
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
    
        public getContent(): UseRankRelativeInstance[] {
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
    
        public static fromDto(dto: PagedGenericDto<UseRankInstanceDto>): PagedUseRankRelativeInstance {
            return new PagedUseRankRelativeInstance(
                dto.content.map(UseRankRelativeInstance.fromDto),
                dto.page,
                dto.size,
                dto.totalElements,
                dto.totalPages
            );
        }
        
        public static empty(): PagedUseRankRelativeInstance {
            return new PagedUseRankRelativeInstance(
                [],
                0,
                0,
                0,
                0
            );
        }
}