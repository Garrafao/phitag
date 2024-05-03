import UsageDto from "../../../phitagdata/usage/dto/UsageDto";
import IInstanceDto from "../../dto/IInstanceDto";
import UsePairInstanceIdDto from "./UsePairInstanceIdDto";

export default interface UsePairInstanceDto extends IInstanceDto {

    readonly id: UsePairInstanceIdDto;

    readonly firstusage: UsageDto;
    readonly secondusage: UsageDto;

    readonly labelSet: Array<string>;
    readonly nonLabel: string;
}