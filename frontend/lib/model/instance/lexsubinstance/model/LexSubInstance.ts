import Usage from "../../../phitagdata/usage/model/Usage";
import { IInstance, IInstanceConstructor } from "../../model/IInstance";
import LexSubInstanceDto from "../dto/LexSubInstanceDto";
import LexSubInstanceId from "./LexSubInstanceId";

export default class LexSubInstance implements IInstance {

    readonly id: LexSubInstanceId;

    private readonly usage: Usage;

    private readonly labelSet: Array<string>;
    private readonly nonLabel: string;

    constructor(id: LexSubInstanceId, usage: Usage, labelSet: Array<string>, nonLabel: string) {
        this.id = id;
        this.usage = usage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public getId(): LexSubInstanceId {
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

    public static fromDto(dto: LexSubInstanceDto): LexSubInstance {
        return new LexSubInstance(
            LexSubInstanceId.fromDto(dto.id),
            Usage.fromDto(dto.usage),
            dto.labelSet,
            dto.nonLabel
        );
    }
    
}

export class LexSubInstanceConstructor implements IInstanceConstructor {
    fromDto(dto: LexSubInstanceDto): LexSubInstance {
        return LexSubInstance.fromDto(dto);
    }
}