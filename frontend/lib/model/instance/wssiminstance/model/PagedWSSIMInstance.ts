import PagedGeneric from "../../../interfaces/PagedGeneric";
import PagedGenericDto from "../../../interfaces/PagedGenericDto";
import WSSIMInstanceDto from "../dto/WSSIMInstanceDto";
import WSSIMInstance from "./WSSIMInstance";

export default class PagedWSSIMInstance implements PagedGeneric<WSSIMInstance> {

    readonly content: WSSIMInstance[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;

    constructor(
        content: WSSIMInstance[],

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

    public getContent(): WSSIMInstance[] {
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

    public static fromDto(dto: PagedGenericDto<WSSIMInstanceDto>): PagedWSSIMInstance {
        return new PagedWSSIMInstance(
            dto.content.map(WSSIMInstance.fromDto),
            dto.page,
            dto.size,
            dto.totalElements,
            dto.totalPages
        );
    }

    public static empty(): PagedWSSIMInstance {
        return new PagedWSSIMInstance(
            [],
            0,
            0,
            0,
            0
        );
    }
    
}
