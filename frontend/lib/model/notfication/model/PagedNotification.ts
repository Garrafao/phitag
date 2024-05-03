import PagedNotificationDto from "../dto/PagedNotificationDto";
import Notification from "./Notification";

export default class PagedNotification {
    private readonly content: Notification[];
    private readonly page: number;
    private readonly size: number;
    private readonly totalElements: number;
    private readonly totalPages: number;

    constructor(
        content: Notification[],
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

    public getContent(): Notification[] {
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

    public static fromDto(dto: PagedNotificationDto): PagedNotification {
        return new PagedNotification(
            dto.content.map(Notification.fromDto),
            dto.page,
            dto.size,
            dto.totalElements,
            dto.totalPages
        );
    }
}