import LexSubInstanceDto from "../../../instance/lexsubinstance/dto/LexSubInstanceDto";
import IJudgementDto from "../../dto/IJudgementDto";
import SpanJudgementIdDto from "./SpanJudgementIdDto";

export default interface SpanJudgementDto extends IJudgementDto {

    readonly id: SpanJudgementIdDto;

    readonly instance: LexSubInstanceDto;

    readonly label: string;
    readonly comment: string;
}