import UseRankInstance from "../../../instance/userankinstance/model/UseRankInstance";
import { IJudgement, IJudgementConstructor } from "../../model/IJudgement";
import UseRankJudgementDto from "../dto/UseRankJudgementDto";
import UseRankJudgementId from "./UseRankJudgementId";

export default class UseRankJudgement implements IJudgement {

    readonly id: UseRankJudgementId;

    readonly instance: UseRankInstance;
    
    readonly label: string;
    readonly comment: string;

    constructor(id: UseRankJudgementId, instance: UseRankInstance, label: string, comment: string) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public getId(): UseRankJudgementId {
        return this.id;
    }

    public getInstance(): UseRankInstance {
        return this.instance;
    }

    public getLabel(): string {
        return this.label;
    }

    public getComment(): string {
        return this.comment;
    }

    public static fromDto(dto: UseRankJudgementDto): UseRankJudgement {
        return new UseRankJudgement(
            UseRankJudgementId.fromDto(dto.id),
            UseRankInstance.fromDto(dto.instance),
            dto.label,
            dto.comment
        );
    }

}

export class UseRankJudgementConstructor implements IJudgementConstructor {
    fromDto(dto: UseRankJudgementDto): UseRankJudgement {
        return UseRankJudgement.fromDto(dto);
    }
}