import IJudgementIdDto from "../dto/IJudgementIdDto";

export interface IJudgementId {
    readonly id: string;
    readonly instanceId: string;
    readonly annotator: string;

    readonly phase: string;
    readonly project: string;
    readonly owner: string;
}

export interface IJudgementIdConstructor {
    fromDto(dto: IJudgementIdDto): IJudgementId;
}