import Usage from "../../../phitagdata/usage/model/Usage";
import { IInstance, IInstanceConstructor } from "../../model/IInstance";
import SpanInstanceDto from "../dto/SentimentInstanceDto";
import SpanInstanceId from "./SpanInstanceId";

export default class SpanInstance implements IInstance {

    readonly id: SpanInstanceId;

    private readonly usage: Usage;

    private readonly labelSet: Array<string>;
    private readonly nonLabel: string;

    constructor(id: SpanInstanceId, usage: Usage, labelSet: Array<string>, nonLabel: string) {
        this.id = id;
        this.usage = usage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public getId(): SpanInstanceId {
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

    public static fromDto(dto: SpanInstanceDto): SpanInstance {
        return new SpanInstance(
            SpanInstanceId.fromDto(dto.id),
            Usage.fromDto(dto.usage),
            dto.labelSet,
            dto.nonLabel
        );
    }
    
}

export class SpanInstanceConstructor implements IInstanceConstructor {
    fromDto(dto: SpanInstanceDto): SpanInstance {
        return SpanInstance.fromDto(dto);
    }
}