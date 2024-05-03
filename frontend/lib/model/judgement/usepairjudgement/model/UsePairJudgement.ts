import UsePairInstance from "../../../instance/usepairinstance/model/UsePairInstance";
import { IJudgement, IJudgementConstructor } from "../../model/IJudgement";
import UsePairJudgementDto from "../dto/UsePairJudgementDto";
import UsePairJudgementId from "./UsePairJudgementId";

export default class UsePairJudgement implements IJudgement {

    readonly id: UsePairJudgementId;

    readonly instance: UsePairInstance;
    
    readonly label: string;
    readonly comment: string;

    constructor(id: UsePairJudgementId, instance: UsePairInstance, label: string, comment: string) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public getId(): UsePairJudgementId {
        return this.id;
    }

    public getInstance(): UsePairInstance {
        return this.instance;
    }

    public getLabel(): string {
        return this.label;
    }

    public getComment(): string {
        return this.comment;
    }

    public static fromDto(dto: UsePairJudgementDto): UsePairJudgement {
        return new UsePairJudgement(
            UsePairJudgementId.fromDto(dto.id),
            UsePairInstance.fromDto(dto.instance),
            dto.label,
            dto.comment
        );
    }

}

export class UsePairJudgementConstructor implements IJudgementConstructor {
    fromDto(dto: UsePairJudgementDto): UsePairJudgement {
        return UsePairJudgement.fromDto(dto);
    }
}