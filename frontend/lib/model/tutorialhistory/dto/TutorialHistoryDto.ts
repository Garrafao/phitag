import AnnotatorIdDto from "../../annotator/dto/AnnotatorIdDto";
import PhaseIdDto from "../../phase/dto/PhaseIdDto";

export default interface TutorialHistoryDto {

    readonly timestamp: string;
    readonly phaseId: PhaseIdDto;
    readonly annotatorId: AnnotatorIdDto;

    readonly measure: number;
    readonly passed: boolean;
}