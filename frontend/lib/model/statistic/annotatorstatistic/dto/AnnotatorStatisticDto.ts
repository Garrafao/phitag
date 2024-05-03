export default interface ProjectStatisticDto {
    readonly annotatorname: string;
    readonly ownername: string;
    readonly projectname: string;

    readonly krippendorffalpha: number;
    readonly interannotatorKrippendorffalpha: Map<string, number>;

    readonly annotations: number;
    readonly phaseAnnotationCountMap: Map<string, number>;
    
}
