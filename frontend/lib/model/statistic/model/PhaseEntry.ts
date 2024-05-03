import Phase from "../../phase/model/Phase";

export default class PhaseEntry {
    readonly phase: Phase;
    readonly numberOfAnnotations: number;

    constructor(phase: Phase, numberOfAnnotations: number) {
        this.phase = phase;
        this.numberOfAnnotations = numberOfAnnotations;
    }
}