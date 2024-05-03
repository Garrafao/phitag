import AnnotatorStatisticDto from "../dto/AnnotatorStatisticDto";

import PhaseEntry from "./PhaseEntry";
import Phase from "../../phase/model/Phase";
import Annotator from "../../annotator/model/Annotator";

export default class AnnotatorStatistic {

    private readonly annotator: Annotator;
    private readonly numberOfAnnotations: number;
    private readonly numberOfAnnotationsPerPhase: Array<PhaseEntry>;
    
    constructor(annotator: Annotator, numberOfAnnotations: number, numberOfAnnotationsPerPhase: Array<PhaseEntry>) {
        this.annotator = annotator;
        this.numberOfAnnotations = numberOfAnnotations;
        this.numberOfAnnotationsPerPhase = numberOfAnnotationsPerPhase;
    }

    public getAnnotator(): Annotator {
        return this.annotator;
    }

    public getNumberOfAnnotations(): number {
        return this.numberOfAnnotations;
    }

    public getNumberOfAnnotationsPerPhase(): Array<PhaseEntry> {
        return this.numberOfAnnotationsPerPhase;
    }

    public static fromDto(dto: AnnotatorStatisticDto): AnnotatorStatistic {
        const numberOfAnnotationsPerPhase = new Array<PhaseEntry>();
        dto.numberOfAnnotationsPerPhase.forEach(phaseEntryDto => {
            numberOfAnnotationsPerPhase.push(new PhaseEntry(Phase.fromDto(phaseEntryDto.phase), phaseEntryDto.numberOfAnnotations));
        });
        return new AnnotatorStatistic(Annotator.fromDto(dto.annotator), dto.numberOfAnnotations, numberOfAnnotationsPerPhase);
    }

}