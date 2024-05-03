import AnnotatorId from "../../annotator/model/AnnotatorId";
import PhaseId from "../../phase/model/PhaseId";
import TutorialHistoryDto from "../dto/TutorialHistoryDto";

export default class TutorialHistory {

    private readonly timestamp: string;
    private readonly phaseId: PhaseId;
    private readonly annotatorId: AnnotatorId;

    private readonly measure: number;
    private readonly passed: boolean;

    constructor(timestamp: string, phaseId: PhaseId, annotatorId: AnnotatorId, measure: number, passed: boolean) {
        this.timestamp = timestamp;
        this.phaseId = phaseId;
        this.annotatorId = annotatorId;
        this.measure = measure;
        this.passed = passed;
    }

    getTimestamp(): string {
        return this.timestamp;
    }

    getPhaseId(): PhaseId {
        return this.phaseId;
    }

    getAnnotatorId(): AnnotatorId {
        return this.annotatorId;
    }

    getMeasure(): number {
        return this.measure;
    }

    isPassed(): boolean {
        return this.passed;
    }

    static fromDto(dto: TutorialHistoryDto): TutorialHistory {
        return new TutorialHistory(dto.timestamp, PhaseId.fromDto(dto.phaseId), AnnotatorId.fromDto(dto.annotatorId), dto.measure, dto.passed);
    }

}