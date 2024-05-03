import IInstanceDto from "../dto/IInstanceDto";
import { IInstanceId } from "./IInstanceId";

export interface IInstance {
    readonly id: IInstanceId;
}

export interface IInstanceConstructor {
    fromDto(dto: IInstanceDto): IInstance;
}