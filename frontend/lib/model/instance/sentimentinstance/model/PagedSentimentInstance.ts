import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import SentimentInstanceDto from "../dto/SentimentInstanceDto";
import SentimentInstance from "./SentimentInstance";

export default class PagedSentimentInstance implements PagedGeneric<SentimentInstance> {
    
        readonly content: SentimentInstance[];
    
        readonly page: number;
        readonly size: number;
        readonly totalElements: number;
        readonly totalPages: number;
    
        constructor(
            content: SentimentInstance[],
            
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
    
        public getContent(): SentimentInstance[] {
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

        public static empty(): PagedSentimentInstance {
            return new PagedSentimentInstance(
                [],
                0,
                0,
                0,
                0
            );
        }
    
        public static fromDto(dto: PagedGenericDto<SentimentInstanceDto>): PagedSentimentInstance {
            return new PagedSentimentInstance(
                dto.content.map(SentimentInstance.fromDto),
                dto.page,
                dto.size,
                dto.totalElements,
                dto.totalPages
            );
        }
        
}