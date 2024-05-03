import AnnotationTypeDto from "../../annotationtype/dto/AnnotationTypeDto";
import Pair from "../../dummy/Pair";
import SamplingDto from "../../sampling/dto/SamplingDto";
import PhaseIdDto from "./PhaseIdDto";

export default interface PhaseDto {

    readonly id: PhaseIdDto;

    readonly displayname: string;

    readonly tutorial: boolean;
    readonly annotationType: AnnotationTypeDto;
    readonly sampling: SamplingDto;

    readonly description: string;
    readonly taskhead: string;

    readonly code: string;


    readonly status: string;

    readonly tutorialrequirements: Array<Pair<string, boolean>>;
}