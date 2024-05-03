import Usage from "../../../phitagdata/usage/model/Usage";
import { IInstance, IInstanceConstructor } from "../../model/IInstance";
import WSSIMTag from "../../wssimtag/model/WSSIMTag";
import WSSIMInstanceDto from "../dto/WSSIMInstanceDto";
import WSSIMInstanceId from "./WSSIMInstanceId";

export default class WSSIMInstance implements IInstance {

    readonly id: WSSIMInstanceId;

    readonly usage: Usage;
    readonly tag: WSSIMTag;

    readonly labelSet: Array<string>;
    readonly nonLabel: string;

    constructor(id: WSSIMInstanceId, usage: Usage, tag: WSSIMTag, labelSet: Array<string>, nonLabel: string) {
        this.id = id;
        this.usage = usage;
        this.tag = tag;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public getId(): WSSIMInstanceId {
        return this.id;
    }

    public getUsage(): Usage {
        return this.usage;
    }

    public getTag(): WSSIMTag {
        return this.tag;
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

    public static fromDto(dto: WSSIMInstanceDto): WSSIMInstance {
        return new WSSIMInstance(
            WSSIMInstanceId.fromDto(dto.id),
            Usage.fromDto(dto.usage),
            WSSIMTag.fromDto(dto.tag),
            dto.labelSet,
            dto.nonLabel
        );
    }
}

export class WSSIMInstanceConstructor implements IInstanceConstructor {
    fromDto(dto: WSSIMInstanceDto): WSSIMInstance {
        return WSSIMInstance.fromDto(dto);
    }
}