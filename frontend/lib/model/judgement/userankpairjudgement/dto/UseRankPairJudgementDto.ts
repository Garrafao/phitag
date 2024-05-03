import UseRankInstanceDto from "../../../instance/userankinstance/dto/UseRankInstanceDto";
import IJudgementDto from "../../dto/IJudgementDto";
import UseRankPairJudgementIdDto from "./UseRankPairJudgementIdDto";

export default interface UseRankPairJudgementDto extends IJudgementDto {

    readonly id: UseRankPairJudgementIdDto;

    readonly instance: UseRankInstanceDto;
    
    readonly label: string;
    readonly comment: string;
}