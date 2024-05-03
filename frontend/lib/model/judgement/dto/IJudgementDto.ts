import AnnotatorDto from "../../annotator/dto/AnnotatorDto";
import IJudgementIdDto from "./IJudgementIdDto";

export default interface IJudgementDto {

    readonly id: IJudgementIdDto;

    readonly label: string;
    readonly comment: string;
}