export default interface PhaseStatisticDto {
    readonly ownername: string;
    readonly projectname: string;
    readonly phasename: string;

    readonly annotations: number;
    readonly annotatorAnnotationCountMap: Map<string, number>;

    readonly krippendorffalpha: number;
    readonly interannotatorKrippendorffalpha: Map<string, number>;
}