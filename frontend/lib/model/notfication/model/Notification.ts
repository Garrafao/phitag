import NotificationDto from "../dto/NotificationDto";

export default class Notification {

    private readonly id: number;
    private readonly message: string;
    private readonly read: boolean;

    constructor(
        id: number,
        message: string,
        read: boolean
    ) {
        this.id = id;
        this.message = message;
        this.read = read;
    }

    public getId(): number {
        return this.id;
    }

    public getMessage(): string {
        return this.message;
    }

    public isRead(): boolean {
        return this.read;
    }

    public static fromDto(dto: NotificationDto): Notification {
        return new Notification(
            dto.id,
            dto.message,
            dto.read
        );
    }

}