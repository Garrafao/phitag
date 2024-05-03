import JoblistingIdDto from "../dto/JoblistingIdDto";

export default class JoblistingId {
    private readonly name;
    private readonly owner;
    private readonly project;

    public constructor(name: string, owner: string, project: string) {
        this.name = name;
        this.owner = owner;
        this.project = project;
    }

    public getName(): string {
        return this.name;
    }

    public getOwner(): string {
        return this.owner;
    }

    public getProject(): string {
        return this.project;
    }

    public static fromDto(dto: JoblistingIdDto): JoblistingId {
        return new JoblistingId(dto.name, dto.owner, dto.project);
    }
}