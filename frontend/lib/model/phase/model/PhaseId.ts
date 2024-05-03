import PhaseIdDto from "../dto/PhaseIdDto";

export default class PhaseId {
    private readonly phase: string;
    private readonly project: string;
    private readonly owner: string;

    constructor(phase: string, project: string, owner: string) {
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    getPhase(): string {
        return this.phase;
    }

    getProject(): string {
        return this.project;
    }

    getOwner(): string {
        return this.owner;
    }

    static fromDto(dto: PhaseIdDto): PhaseId {
        return new PhaseId(dto.phase, dto.project, dto.owner);
    }
}