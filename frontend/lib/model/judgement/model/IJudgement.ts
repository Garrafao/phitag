import IJudgementDto from "../dto/IJudgementDto";
import { IJudgementId } from "./IJudgementId";

export interface IJudgement {

    readonly id: IJudgementId;

    readonly label: string;
    readonly comment: string;

}

export interface IJudgementConstructor {
    fromDto(dto: IJudgementDto): IJudgement;
}