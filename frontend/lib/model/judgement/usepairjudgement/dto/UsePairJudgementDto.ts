import UsePairInstanceDto from "../../../instance/usepairinstance/dto/UsePairInstanceDto";
import IJudgementDto from "../../dto/IJudgementDto";
import UsePairJudgementIdDto from "./UsePairJudgementIdDto";

export default interface UsePairJudgementDto extends IJudgementDto {

    readonly id: UsePairJudgementIdDto;

    readonly instance: UsePairInstanceDto;
    
    readonly label: string;
    readonly comment: string;
}