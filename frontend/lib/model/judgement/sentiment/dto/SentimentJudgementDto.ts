import LexSubInstanceDto from "../../../instance/lexsubinstance/dto/LexSubInstanceDto";
import SentimentInstanceDto from "../../../instance/sentimentinstance/dto/SentimentInstanceDto";
import IJudgementDto from "../../dto/IJudgementDto";
import SentimentJudgementIdDto from "./SentimentJudgementIdDto";

export default interface SentimentJudgementDto extends IJudgementDto {

    readonly id: SentimentJudgementIdDto;

    readonly instance: SentimentInstanceDto;

    readonly label: string;
    readonly comment: string;
}