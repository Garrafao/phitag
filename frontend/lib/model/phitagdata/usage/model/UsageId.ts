import UsageIdDto from "../dto/UsageIdDto";

export default class UsageId {
    private readonly dataid: string;
    private readonly project: string;
    private readonly owner: string;

    constructor(dataid: string, project: string, owner: string) {
        this.dataid = dataid;
        this.project = project;
        this.owner = owner;
    }

    getDataid(): string {
        return this.dataid;
    }

    getProject(): string {
        return this.project;
    }

    getOwner(): string {
        return this.owner;
    }

    static fromDto(dto: UsageIdDto): UsageId {
        return new UsageId(dto?.dataid, dto?.project, dto?.owner);
    }
}