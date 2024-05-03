import AnnotatorDto from "../../annotator/dto/AnnotatorDto";
import PhaseEntryDto from "./PhaseEntryDto";

export default interface AnnotatorStatisticDto {
    readonly annotator: AnnotatorDto;
    readonly numberOfAnnotations: number;
    readonly numberOfAnnotationsPerPhase: Array<PhaseEntryDto>;
}