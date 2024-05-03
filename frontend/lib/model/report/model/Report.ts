import Status from "../../status/model/Status";
import ReportDto from "../dto/ReportDto";

export default class Report {

    private readonly id: string;

    private readonly user: string;
    private readonly status: Status;
    private readonly description: string;

    constructor(
        id: string,

        user: string,
        status: Status,
        description: string
    ) {
        this.id = id;

        this.user = user;
        this.status = status;
        this.description = description;
    }

    public getId(): string {
        return this.id;
    }

    public getUser(): string {
        return this.user;
    }

    public getStatus(): Status {
        return this.status;
    }

    public getDescription(): string {
        return this.description;
    }

    public static fromDto(dto: ReportDto): Report {
        return new Report(
            dto.id,
            dto.user,
            Status.fromDto(dto.status),
            dto.description
        );
    }
}