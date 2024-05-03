import ProjectIdDto from "../dto/ProjectIdDto";

export default class ProjectId {
    private readonly name: string;
    private readonly owner: string;

    constructor(name: string, owner: string) {
        this.name = name;
        this.owner = owner;
    }

    getName(): string {
        return this.name;
    }

    getOwner(): string {
        return this.owner;
    }

    static fromDto(dto: ProjectIdDto): ProjectId {
        return new ProjectId(dto.name, dto.owner);
    }
}