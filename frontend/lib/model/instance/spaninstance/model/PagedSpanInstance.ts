import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import SpanInstanceDto from "../dto/SentimentInstanceDto";
import SpanInstance from "./SpanInstance";

export default class PagedSpanInstance implements PagedGeneric<SpanInstance> {
    
        readonly content: SpanInstance[];
    
        readonly page: number;
        readonly size: number;
        readonly totalElements: number;
        readonly totalPages: number;
    
        constructor(
            content: SpanInstance[],
            
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
    
        public getContent(): SpanInstance[] {
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

        public static empty(): PagedSpanInstance {
            return new PagedSpanInstance(
                [],
                0,
                0,
                0,
                0
            );
        }
    
        public static fromDto(dto: PagedGenericDto<SpanInstanceDto>): PagedSpanInstance {
            return new PagedSpanInstance(
                dto.content.map(SpanInstance.fromDto),
                dto.page,
                dto.size,
                dto.totalElements,
                dto.totalPages
            );
        }
        
}