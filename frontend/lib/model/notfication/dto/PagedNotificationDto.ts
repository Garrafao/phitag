import NotificationDto from "./NotificationDto";

export default interface PagedNotificationDto {
    readonly content: NotificationDto[];

    readonly page: number;
    readonly size: number;
    readonly totalElements: number;
    readonly totalPages: number;
}