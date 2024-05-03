import WSSIMInstance from "../../../instance/wssiminstance/model/WSSIMInstance";
import { IJudgement, IJudgementConstructor } from "../../model/IJudgement";
import WSSIMJudgementDto from "../dto/WSSIMJudgementDto";
import WSSIMJudgementId from "./WSSIMJudgementId";

export default class WSSIMJudgement implements IJudgement {

    readonly id: WSSIMJudgementId;

    readonly instance: WSSIMInstance;

    readonly label: string;
    readonly comment: string;

    constructor(id: WSSIMJudgementId, instance: WSSIMInstance, label: string, comment: string) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public getId(): WSSIMJudgementId {
        return this.id;
    }


    public getInstance(): WSSIMInstance {
        return this.instance;
    }

    public getLabel(): string {
        return this.label;
    }

    public getComment(): string {
        return this.comment;
    }

    public static fromDto(dto: WSSIMJudgementDto): WSSIMJudgement {
        return new WSSIMJudgement(
            WSSIMJudgementId.fromDto(dto.id),
            WSSIMInstance.fromDto(dto.instance),
            dto.label,
            dto.comment
        );
    }

}

export class WSSIMJudgementConstructor implements IJudgementConstructor {
    fromDto(dto: WSSIMJudgementDto): WSSIMJudgement {
        return WSSIMJudgement.fromDto(dto);
    }
}