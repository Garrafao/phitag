import IJudgementIdDto from "../../dto/IJudgementIdDto";
import { IJudgementId, IJudgementIdConstructor } from "../../model/IJudgementId";

export default class UsePairJudgementId implements IJudgementId {

    readonly id: string;

    readonly instanceId: string;
    readonly annotator: string;
    
    readonly phase: string;
    readonly project: string;
    readonly owner: string;

    constructor(id: string, instanceId: string, annotator: string, phase: string, project: string, owner: string) {
        this.id = id;
        this.instanceId = instanceId;
        this.annotator = annotator;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public getId(): string {
        return this.id;
    }

    public getInstanceId(): string {
        return this.instanceId;
    }

    public getAnnotator(): string {
        return this.annotator;
    }

    public getPhase(): string {
        return this.phase;
    }

    public getProject(): string {
        return this.project;
    }

    public getOwner(): string {
        return this.owner;
    }

    static fromDto(dto: IJudgementIdDto): UsePairJudgementId {
        return new UsePairJudgementId(
            dto.id,
            dto.instanceId,
            dto.annotator,
            dto.phase,
            dto.project,
            dto.owner
        );
    }
}

export class UsePairJudgementIdConstructor implements IJudgementIdConstructor {
    fromDto(dto: IJudgementIdDto): UsePairJudgementId {
        return UsePairJudgementId.fromDto(dto);
    }
}