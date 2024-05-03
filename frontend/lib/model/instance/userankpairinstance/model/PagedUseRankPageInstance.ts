import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import UseRankPairInstanceDto from "../dto/UseRankPairInstanceDto";
import UseRankPairInstance from "./UseRankPairInstance";


export default class PagedUseRankPairInstance implements PagedGeneric<UseRankPairInstance> {
    
        readonly content: UseRankPairInstance[];
    
        readonly page: number;
        readonly size: number;
        readonly totalElements: number;
        readonly totalPages: number;
    
        constructor(
            content: UseRankPairInstance[],
            
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
    
        public getContent(): UseRankPairInstance[] {
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
    
        public static fromDto(dto: PagedGenericDto<UseRankPairInstanceDto>): PagedUseRankPairInstance {
            return new PagedUseRankPairInstance(
                dto.content.map(UseRankPairInstance.fromDto),
                dto.page,
                dto.size,
                dto.totalElements,
                dto.totalPages
            );
        }
        
        public static empty(): PagedUseRankPairInstance {
            return new PagedUseRankPairInstance(
                [],
                0,
                0,
                0,
                0
            );
        }
}