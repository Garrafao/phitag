import PhaseDto from "../../phase/dto/PhaseDto";

export default interface PhaseEntryDto {
    readonly phase: PhaseDto;
    readonly numberOfAnnotations: number;
}