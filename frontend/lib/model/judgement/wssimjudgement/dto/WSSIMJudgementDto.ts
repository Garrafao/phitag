import WSSIMInstanceDto from "../../../instance/wssiminstance/dto/WSSIMInstanceDto";
import IJudgementDto from "../../dto/IJudgementDto";
import WSSIMJudgementIdDto from "./WSSIMJudgementIdDto";

export default interface WSSIMJudgementDto extends IJudgementDto {

    readonly id: WSSIMJudgementIdDto;

    readonly instance: WSSIMInstanceDto;
    
    readonly label: string;
    readonly comment: string;
}
    