import Usage from "../../../phitagdata/usage/model/Usage";
import { IInstance, IInstanceConstructor } from "../../model/IInstance";
import UseRankRelativeInstanceDto from "../dto/UseRankRelativeInstanceDto";
import UseRankRelativeInstanceId from "./UseRankRelativeInstanceId";



export default class UseRankRelativeInstance implements IInstance {

    readonly id: UseRankRelativeInstanceId;

    private readonly firstusage: Usage;
    private readonly secondusage: Usage;
    private readonly thirdusage: Usage;
    private readonly fourthusage: Usage;
    private readonly fifthusage: Usage;
    private readonly sixthusage: Usage;
    private readonly seventhusage: Usage;
    private readonly eightusage: Usage;
    private readonly ninthusage: Usage;
    private readonly tenthusage: Usage;


    private readonly labelSet: Array<string>;
    private readonly nonLabel: string;

    constructor(id: UseRankRelativeInstanceId, firstUsage: Usage, secondUsage: Usage,
        thirdusage: Usage, fourthusage: Usage,
        fifthusage: Usage, sixthusage: Usage,
        seventhusage: Usage, eightusage: Usage,
        ninthusage: Usage, tenthusage: Usage,
        labelSet: Array<string>, nonLabel: string) {
        this.id = id;
        this.firstusage = firstUsage;
        this.secondusage = secondUsage;
        this.thirdusage = thirdusage;
        this.fourthusage = fourthusage;
        this.fifthusage = fifthusage;
        this.sixthusage = sixthusage;
        this.seventhusage = seventhusage;
        this.eightusage = eightusage;
        this.ninthusage = ninthusage;
        this.tenthusage = tenthusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public getId(): UseRankRelativeInstanceId {
        return this.id;
    }

    public getFirstusage(): Usage {
        return this.firstusage;
    }

    public getSecondusage(): Usage {
        return this.secondusage;
    }
    public getThirdusage(): Usage {
        return this.thirdusage;
    }

    public getFourthusage(): Usage {
        return this.fourthusage;
    }
    public getFifthusage(): Usage {
        return this.fifthusage;
    }
    public getSixthusage(): Usage {
        return this.sixthusage;
    }
    public getSeventhusage(): Usage {
        return this.seventhusage;
    }
    public getEightusage(): Usage {
        return this.eightusage;
    }
    public getNinthusage(): Usage {
        return this.ninthusage;
    }
    public getTenthusage(): Usage {
        return this.tenthusage;
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

    public static fromDto(dto: UseRankRelativeInstanceDto): UseRankRelativeInstance {
        return new UseRankRelativeInstance(
            UseRankRelativeInstanceId.fromDto(dto.id),
            Usage.fromDto(dto.firstusage) ?? null,
            Usage.fromDto(dto.secondusage) ?? null,
            Usage.fromDto(dto.thirdusage) ?? null,
            Usage.fromDto(dto.fourthusage) ?? null,
            Usage.fromDto(dto.fifthusage) ?? null,
            Usage.fromDto(dto.sixthusage) ?? null,
            Usage.fromDto(dto.seventhusage) ?? null,
            Usage.fromDto(dto.eightusage) ?? null,
            Usage.fromDto(dto.ninthusage) ?? null,
            Usage.fromDto(dto.tenthusage) ?? null,
            dto.labelSet,
            dto.nonLabel
        );
    }
}

export class UseRankRelativeInstanceConstructor implements IInstanceConstructor {
    fromDto(dto: UseRankRelativeInstanceDto): UseRankRelativeInstance {
        return UseRankRelativeInstance.fromDto(dto);
    }
} 

