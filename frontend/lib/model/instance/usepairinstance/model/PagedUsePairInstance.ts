import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import UsePairInstanceDto from "../dto/UsePairInstanceDto";
import UsePairInstance from "./UsePairInstance";

export default class PagedUsePairInstance implements PagedGeneric<UsePairInstance> {
    
        readonly content: UsePairInstance[];
    
        readonly page: number;
        readonly size: number;
        readonly totalElements: number;
        readonly totalPages: number;
    
        constructor(
            content: UsePairInstance[],
            
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
    
        public getContent(): UsePairInstance[] {
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
    
        public static fromDto(dto: PagedGenericDto<UsePairInstanceDto>): PagedUsePairInstance {
            return new PagedUsePairInstance(
                dto.content.map(UsePairInstance.fromDto),
                dto.page,
                dto.size,
                dto.totalElements,
                dto.totalPages
            );
        }
        
        public static empty(): PagedUsePairInstance {
            return new PagedUsePairInstance(
                [],
                0,
                0,
                0,
                0
            );
        }
}