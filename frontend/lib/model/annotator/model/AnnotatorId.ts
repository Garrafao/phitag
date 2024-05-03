import AnnotatorIdDto from "../dto/AnnotatorIdDto";

export default class AnnotatorId {
    private readonly user: string;
    private readonly project: string;
    private readonly owner: string;

    constructor(user: string, project: string, owner: string) {
        this.user = user;
        this.project = project;
        this.owner = owner;
    }

    public getUser(): string {
        return this.user;
    }

    public getProject(): string {
        return this.project;
    }

    public getOwner(): string {
        return this.owner;
    }

    static fromDto(dto: AnnotatorIdDto): AnnotatorId {
        return new AnnotatorId(dto.user, dto.project, dto.owner);
    }

}