import UseRankPairInstance from "../../../instance/userankpairinstance/model/UseRankPairInstance";
import { IJudgement, IJudgementConstructor } from "../../model/IJudgement";
import UseRankPairJudgementDto from "../dto/UseRankPairJudgementDto";
import UseRankPairJudgementId from "./UseRankPairJudgementId";

export default class UseRankPairJudgement implements IJudgement {

    readonly id: UseRankPairJudgementId;

    readonly instance: UseRankPairInstance;
    
    readonly label: string;
    readonly comment: string;

    constructor(id: UseRankPairJudgementId, instance: UseRankPairInstance, label: string, comment: string) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public getId(): UseRankPairJudgementId {
        return this.id;
    }

    public getInstance(): UseRankPairInstance {
        return this.instance;
    }

    public getLabel(): string {
        return this.label;
    }

    public getComment(): string {
        return this.comment;
    }

    public static fromDto(dto: UseRankPairJudgementDto): UseRankPairJudgement {
        return new UseRankPairJudgement(
            UseRankPairJudgementId.fromDto(dto.id),
            UseRankPairInstance.fromDto(dto.instance),
            dto.label,
            dto.comment
        );
    }

}

export class UseRankPairJudgementConstructor implements IJudgementConstructor {
    fromDto(dto: UseRankPairJudgementDto): UseRankPairJudgement {
        return UseRankPairJudgement.fromDto(dto);
    }
}