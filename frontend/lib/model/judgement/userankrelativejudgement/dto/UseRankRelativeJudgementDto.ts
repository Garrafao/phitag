import UseRankInstanceDto from "../../../instance/userankinstance/dto/UseRankInstanceDto";
import IJudgementDto from "../../dto/IJudgementDto";
import UseRankJudgementIdDto from "../../userankjudgement/dto/UseRankJudgementIdDto";

export default interface UseRankRelativeJudgementDto extends IJudgementDto {

    readonly id: UseRankJudgementIdDto;

    readonly instance: UseRankInstanceDto;
    
    readonly label: string;
    readonly comment: string;
}