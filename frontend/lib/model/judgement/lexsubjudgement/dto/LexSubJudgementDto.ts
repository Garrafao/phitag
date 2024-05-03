import LexSubInstanceDto from "../../../instance/lexsubinstance/dto/LexSubInstanceDto";
import IJudgementDto from "../../dto/IJudgementDto";
import LexSubJudgementIdDto from "./LexSubJudgementIdDto";

export default interface LexSubJudgementDto extends IJudgementDto {

    readonly id: LexSubJudgementIdDto;

    readonly instance: LexSubInstanceDto;

    readonly label: string;
    readonly comment: string;
}