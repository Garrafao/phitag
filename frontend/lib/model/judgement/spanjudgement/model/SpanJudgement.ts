import LexSubInstance from "../../../instance/lexsubinstance/model/LexSubInstance";
import SpanInstance from "../../../instance/spaninstance/model/SpanInstance";
import { IJudgement, IJudgementConstructor } from "../../model/IJudgement";
import SpanJudgementDto from "../dto/SpanJudgementDto";
import SpanJudgementId from "./SpanJudgementId";

export default class SpanJudgement implements IJudgement {

    readonly id: SpanJudgementId;

    readonly instance: SpanInstance;

    readonly label: string;
    readonly comment: string;

    constructor(id: SpanJudgementId, instance: SpanInstance, label: string, comment: string) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public getId(): SpanJudgementId {
        return this.id;
    }

    public getInstance(): SpanInstance {
        return this.instance;
    }

    public getLabel(): string {
        return this.label;
    }

    public getComment(): string {
        return this.comment;
    }

    public static fromDto(dto: SpanJudgementDto): SpanJudgement {
        return new SpanJudgement(
            SpanJudgementId.fromDto(dto.id),
            SpanInstance.fromDto(dto.instance),
            dto.label,
            dto.comment
        );
    }

}

export class SpanJudgementConstructor implements IJudgementConstructor {
    fromDto(dto: SpanJudgementDto): SpanJudgement {
        return SpanJudgement.fromDto(dto);
    }
}