import LexSubInstance from "../../../instance/lexsubinstance/model/LexSubInstance";
import { IJudgement, IJudgementConstructor } from "../../model/IJudgement";
import LexSubJudgementDto from "../dto/LexSubJudgementDto";
import LexSubJudgementId from "./LexSubJudgementId";

export default class LexSubJudgement implements IJudgement {

    readonly id: LexSubJudgementId;

    readonly instance: LexSubInstance;

    readonly label: string;
    readonly comment: string;

    constructor(id: LexSubJudgementId, instance: LexSubInstance, label: string, comment: string) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public getId(): LexSubJudgementId {
        return this.id;
    }

    public getInstance(): LexSubInstance {
        return this.instance;
    }

    public getLabel(): string {
        return this.label;
    }

    public getComment(): string {
        return this.comment;
    }

    public static fromDto(dto: LexSubJudgementDto): LexSubJudgement {
        return new LexSubJudgement(
            LexSubJudgementId.fromDto(dto.id),
            LexSubInstance.fromDto(dto.instance),
            dto.label,
            dto.comment
        );
    }

}

export class LexSubJudgementConstructor implements IJudgementConstructor {
    fromDto(dto: LexSubJudgementDto): LexSubJudgement {
        return LexSubJudgement.fromDto(dto);
    }
}