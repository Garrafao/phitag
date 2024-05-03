import UsageDto from "../../../phitagdata/usage/dto/UsageDto";
import IInstanceDto from "../../dto/IInstanceDto";
import UseRankInstanceIdDto from "./UseRankInstanceIdDto";

export default interface UseRankInstanceDto extends IInstanceDto {

    readonly id: UseRankInstanceIdDto;

    readonly firstusage: UsageDto;
    readonly secondusage: UsageDto;
    readonly thirdusage: UsageDto;
    readonly fourthusage: UsageDto;
    readonly fifthusage: UsageDto;
    readonly sixthusage: UsageDto;
    readonly seventhusage: UsageDto;
    readonly eightusage: UsageDto;
    readonly ninthusage: UsageDto;
    readonly tenthusage: UsageDto;

    readonly labelSet: Array<string>;
    readonly nonLabel: string;
}