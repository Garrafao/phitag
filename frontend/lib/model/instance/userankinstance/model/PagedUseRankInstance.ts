import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import UseRankInstanceDto from "../dto/UseRankInstanceDto";
import UseRankInstanceIdDto from "../dto/UseRankInstanceIdDto";
import UseRankInstance from "./UseRankInstance";

export default class PagedUseRankInstance implements PagedGeneric<UseRankInstance> {
    
        readonly content: UseRankInstance[];
    
        readonly page: number;
        readonly size: number;
        readonly totalElements: number;
        readonly totalPages: number;
    
        constructor(
            content: UseRankInstance[],
            
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
    
        public getContent(): UseRankInstance[] {
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
    
        public static fromDto(dto: PagedGenericDto<UseRankInstanceDto>): PagedUseRankInstance {
            return new PagedUseRankInstance(
                dto.content.map(UseRankInstance.fromDto),
                dto.page,
                dto.size,
                dto.totalElements,
                dto.totalPages
            );
        }
        
        public static empty(): PagedUseRankInstance {
            return new PagedUseRankInstance(
                [],
                0,
                0,
                0,
                0
            );
        }
}