import SentimentInstance from "../../../instance/sentimentinstance/model/SentimentInstance";
import { IJudgement, IJudgementConstructor } from "../../model/IJudgement";
import SentimentJudgementDto from "../dto/SentimentJudgementDto";
import SentimentJudgementId from "./SentimentJudgementId";


export default class SentimentJudgement implements IJudgement {

    readonly id: SentimentJudgementId;

    readonly instance: SentimentInstance;

    readonly label: string;
    readonly comment: string;

    constructor(id: SentimentJudgementId, instance: SentimentInstance, label: string, comment: string) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public getId(): SentimentJudgementId {
        return this.id;
    }

    public getInstance(): SentimentInstance {
        return this.instance;
    }

    public getLabel(): string {
        return this.label;
    }

    public getComment(): string {
        return this.comment;
    }

    public static fromDto(dto: SentimentJudgementDto): SentimentJudgement {
        return new SentimentJudgement(
            SentimentJudgementId.fromDto(dto.id),
            SentimentInstance.fromDto(dto.instance),
            dto.label,
            dto.comment
        );
    }

}

export class SentimentJudgementConstructor implements IJudgementConstructor {
    fromDto(dto: SentimentJudgementDto): SentimentJudgement {
        return SentimentJudgement.fromDto(dto);
    }
}