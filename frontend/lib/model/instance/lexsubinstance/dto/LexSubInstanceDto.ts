import UsageDto from "../../../phitagdata/usage/dto/UsageDto";
import IInstanceDto from "../../dto/IInstanceDto";
import LexSubInstanceIdDto from "./LexSubInstanceIdDto";

export default interface LexSubInstanceDto extends IInstanceDto {

    readonly id: LexSubInstanceIdDto;

    readonly usage: UsageDto;

    readonly labelSet: Array<string>;
    readonly nonLabel: string;
}