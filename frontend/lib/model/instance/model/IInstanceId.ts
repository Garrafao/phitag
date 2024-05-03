import IInstanceIdDto from "../dto/IInstanceIdDto";

export interface IInstanceId {
    
    readonly instanceId: string;

    readonly phase: string;
    readonly project: string;
    readonly owner: string;
}

export interface IInstanceIdConstructor {
    fromDto(dto: IInstanceIdDto): IInstanceId;
}
