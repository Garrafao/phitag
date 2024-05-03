import UsageDto from "../../../phitagdata/usage/dto/UsageDto";
import IInstanceDto from "../../dto/IInstanceDto";
import WSSIMTagDto from "../../wssimtag/dto/WSSIMTagDto";
import WSSIMInstanceIdDto from "./WSSIMInstanceIdDto";

export default interface WSSIMInstanceDto extends IInstanceDto {

    readonly id: WSSIMInstanceIdDto;

    readonly usage: UsageDto;
    readonly tag: WSSIMTagDto;
    
    readonly labelSet: Array<string>;
    readonly nonLabel: string;

}