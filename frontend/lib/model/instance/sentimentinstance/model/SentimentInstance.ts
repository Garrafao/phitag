import Usage from "../../../phitagdata/usage/model/Usage";
import { IInstance, IInstanceConstructor } from "../../model/IInstance";
import SentimentInstanceDto from "../dto/SentimentInstanceDto";
import SentimentInstanceId from "./SentimentInstanceId";

export default class SentimentInstance implements IInstance {

    readonly id: SentimentInstanceId;

    private readonly usage: Usage;

    private readonly labelSet: Array<string>;
    private readonly nonLabel: string;

    constructor(id: SentimentInstanceId, usage: Usage, labelSet: Array<string>, nonLabel: string) {
        this.id = id;
        this.usage = usage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public getId(): SentimentInstanceId {
        return this.id;
    }

    public getUsage(): Usage {
        return this.usage;
    }

    public getLabelSet(): Array<string> {
        return this.labelSet;
    }

    public getNonLabel(): string {
        return this.nonLabel;
    }

    public getLabelAndNonLabel(): Array<string> {
        return this.labelSet.concat(this.nonLabel);
    }

    public static fromDto(dto: SentimentInstanceDto): SentimentInstance {
        return new SentimentInstance(
            SentimentInstanceId.fromDto(dto.id),
            Usage.fromDto(dto.usage),
            dto.labelSet,
            dto.nonLabel
        );
    }
    
}

export class SentimentInstanceConstructor implements IInstanceConstructor {
    fromDto(dto: SentimentInstanceDto): SentimentInstance {
        return SentimentInstance.fromDto(dto);
    }
}