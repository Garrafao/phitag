import PhaseStatisticDto from "../dto/PhaseStatisticDto";

export default class PhaseStatistic {

    private readonly ownername: string;
    private readonly projectname: string;
    private readonly phasename: string;

    private readonly annotations: number;
    private readonly annotatorAnnotationCountMap: Map<string, number>;

    private readonly krippendorffalpha: number;
    private readonly interannotatorKrippendorffalpha: Map<string, number>;

    constructor(
        ownername: string,
        projectname: string,
        phasename: string,

        annotations: number,
        annotatorAnnotationCountMap: Map<string, number>,

        krippendorffalpha: number,
        interannotatorKrippendorffalpha: Map<string, number>) {

        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;

        this.annotations = annotations;
        this.annotatorAnnotationCountMap = new Map<string, number>(Object.entries(annotatorAnnotationCountMap));

        this.krippendorffalpha = krippendorffalpha;
        this.interannotatorKrippendorffalpha = new Map<string, number>(Object.entries(interannotatorKrippendorffalpha));

    }

    public getOwnername(): string {
        return this.ownername;
    }

    public getProjectname(): string {
        return this.projectname;
    }

    public getPhasename(): string {
        return this.phasename;
    }

    public getAnnotations(): number {
        return this.annotations;
    }

    public getAnnotatorAnnotationCountMap(): Map<string, number> {
        return this.annotatorAnnotationCountMap;
    }

    public getKrippendorffalpha(): number {
        return this.krippendorffalpha;
    }

    public getInterannotatorKrippendorffalpha(): Map<string, number> {
        return this.interannotatorKrippendorffalpha;
    }

    public static fromDto(dto: PhaseStatisticDto): PhaseStatistic {
        return new PhaseStatistic(
            dto.ownername,
            dto.projectname,
            dto.phasename,

            dto.annotations,
            dto.annotatorAnnotationCountMap,

            dto.krippendorffalpha,
            dto.interannotatorKrippendorffalpha);
    }
}