import UsageDto from "../../../phitagdata/usage/dto/UsageDto";
import IInstanceDto from "../../dto/IInstanceDto";
import SpanInstanceIdDto from "./SpanInstanceIdDto";

export default interface SpanInstanceDto extends IInstanceDto {

    readonly id: SpanInstanceIdDto;

    readonly usage: UsageDto;

    readonly labelSet: Array<string>;
    readonly nonLabel: string;
}