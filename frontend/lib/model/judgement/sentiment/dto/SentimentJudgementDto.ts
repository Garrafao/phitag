import LexSubInstanceDto from "../../../instance/lexsubinstance/dto/LexSubInstanceDto";
import IJudgementDto from "../../dto/IJudgementDto";
import SentimentJudgementIdDto from "./SentimentJudgementIdDto";

export default interface SentimentJudgementDto extends IJudgementDto {

    readonly id: SentimentJudgementIdDto;

    readonly instance: LexSubInstanceDto;

    readonly label: string;
    readonly comment: string;
}