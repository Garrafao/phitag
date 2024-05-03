import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import LexSubInstanceDto from "../dto/LexSubInstanceDto";
import LexSubInstance from "./LexSubInstance";

export default class PagedLexSubInstance implements PagedGeneric<LexSubInstance> {
    
        readonly content: LexSubInstance[];
    
        readonly page: number;
        readonly size: number;
        readonly totalElements: number;
        readonly totalPages: number;
    
        constructor(
            content: LexSubInstance[],
            
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
    
        public getContent(): LexSubInstance[] {
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

        public static empty(): PagedLexSubInstance {
            return new PagedLexSubInstance(
                [],
                0,
                0,
                0,
                0
            );
        }
    
        public static fromDto(dto: PagedGenericDto<LexSubInstanceDto>): PagedLexSubInstance {
            return new PagedLexSubInstance(
                dto.content.map(LexSubInstance.fromDto),
                dto.page,
                dto.size,
                dto.totalElements,
                dto.totalPages
            );
        }
        
}