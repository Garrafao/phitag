import UseRankRelativeInstance from "../../../instance/userankreltiveinstance/model/UseRankRelativeInstance";
import { IJudgement, IJudgementConstructor } from "../../model/IJudgement";
import UseRankRelativeJudgementDto from "../dto/UseRankRelativeJudgementDto";
import UseRankRelativeJudgementId from "./UseRankRelativeJudgementId";

export default class UseRankRelativeJudgement implements IJudgement {

    readonly id: UseRankRelativeJudgementId;

    readonly instance: UseRankRelativeInstance;
    
    readonly label: string;
    readonly comment: string;

    constructor(id: UseRankRelativeJudgementId, instance: UseRankRelativeInstance, label: string, comment: string) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public getId(): UseRankRelativeJudgementId {
        return this.id;
    }

    public getInstance(): UseRankRelativeInstance {
        return this.instance;
    }

    public getLabel(): string {
        return this.label;
    }

    public getComment(): string {
        return this.comment;
    }

    public static fromDto(dto: UseRankRelativeJudgementDto): UseRankRelativeJudgement {
        return new UseRankRelativeJudgement(
            UseRankRelativeJudgementId.fromDto(dto.id),
            UseRankRelativeInstance.fromDto(dto.instance),
            dto.label,
            dto.comment
        );
    }

}

export class UseRankRelativeJudgementConstructor implements IJudgementConstructor {
    fromDto(dto: UseRankRelativeJudgementDto): UseRankRelativeJudgement {
        return UseRankRelativeJudgement.fromDto(dto);
    }
}