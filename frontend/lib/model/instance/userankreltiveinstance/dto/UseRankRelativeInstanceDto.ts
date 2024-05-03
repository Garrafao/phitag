import UsageDto from "../../../phitagdata/usage/dto/UsageDto";
import IInstanceDto from "../../dto/IInstanceDto";
import UseRankRelativeInstanceIdDto from "./UseRankRelativeInstanceIdDto";

export default interface UseRankRelativeInstanceDto extends IInstanceDto {

    readonly id: UseRankRelativeInstanceIdDto;

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