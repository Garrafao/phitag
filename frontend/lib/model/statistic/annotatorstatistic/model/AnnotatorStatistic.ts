import AnnotatorStatisticDto from "../dto/AnnotatorStatisticDto";

export default class AnnotatorStatistic {

    private readonly annotatorname: string;
    private readonly ownername: string;
    private readonly projectname: string;

    private readonly krippendorffalpha: number;
    private readonly interannotatorKrippendorffalpha: Map<string, number>;

    private readonly annotations: number;
    private readonly phaseAnnotationCountMap: Map<string, number>;

    constructor(
        annotatorname: string,
        ownername: string,
        projectname: string,

        krippendorffalpha: number,
        interannotatorKrippendorffalpha: Map<string, number>,

        annotations: number,
        phaseAnnotationCountMap: Map<string, number>) {
        this.annotatorname = annotatorname;
        this.ownername = ownername;
        this.projectname = projectname;

        this.krippendorffalpha = krippendorffalpha;
        this.interannotatorKrippendorffalpha = new Map<string, number>(Object.entries(interannotatorKrippendorffalpha));

        this.annotations = annotations;
        this.phaseAnnotationCountMap = new Map<string, number>(Object.entries(phaseAnnotationCountMap));
    }

    public getAnnotatorname(): string {
        return this.annotatorname;
    }

    public getOwnername(): string {
        return this.ownername;
    }

    public getProjectname(): string {
        return this.projectname;
    }

    public getKrippendorffalpha(): number {
        return this.krippendorffalpha;
    }

    public getInterannotatorKrippendorffalpha(): Map<string, number> {
        return this.interannotatorKrippendorffalpha;
    }

    public getAnnotations(): number {
        return this.annotations;
    }

    public getPhaseAnnotationCountMap(): Map<string, number> {
        return this.phaseAnnotationCountMap;
    }

    public static fromDto(dto: AnnotatorStatisticDto): AnnotatorStatistic {
        return new AnnotatorStatistic(
            dto.annotatorname,
            dto.ownername,
            dto.projectname,

            dto.krippendorffalpha,
            dto.interannotatorKrippendorffalpha,

            dto.annotations,
            dto.phaseAnnotationCountMap);
    }

}