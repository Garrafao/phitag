import UsageDto from "../../../phitagdata/usage/dto/UsageDto";
import IInstanceDto from "../../dto/IInstanceDto";
import SentimentInstanceIdDto from "./SentimentInstanceIdDto";

export default interface SentimentInstanceDto extends IInstanceDto {

    readonly id: SentimentInstanceIdDto;

    readonly usage: UsageDto;

    readonly labelSet: Array<string>;
    readonly nonLabel: string;
}