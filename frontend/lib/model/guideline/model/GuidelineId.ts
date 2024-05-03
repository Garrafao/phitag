import GuidelineIdDto from "../dto/GuidelineIdDto";

export default class GuidelineId {

    private readonly name: string;
    private readonly project: string;
    private readonly owner: string;

    constructor(name: string, project: string, owner: string) {
        this.name = name;
        this.project = project;
        this.owner = owner;
    }

    public getName(): string {
        return this.name;
    }

    public getProject(): string {
        return this.project;
    }

    public getOwner(): string {
        return this.owner;
    }

    public static fromDto(dto: GuidelineIdDto): GuidelineId {
        return new GuidelineId(
            dto.name,
            dto.project,
            dto.owner
        );
    }
}