import AnnotationStaticHistoryDto from "../dto/AnnotatorStaticHistoryDto";


export default class AnnotationHistoryTable  {

    private readonly uuid: string;

    private readonly annotatorname: string;

    private readonly goldannotator: string;
    private readonly ownername: string;
    private readonly projectname: string;

    private readonly phasename: string;
    private readonly annotationmeasure: string;
    private readonly agreement: number;


    constructor(uuid: string, annotatorname: string, goldannotator: string, ownername: string,
         projectname: string, phasename: string, annotationmeasure: string
        , agreement: number) {
        this.uuid = uuid;
        this.annotatorname = annotatorname;
        this.goldannotator = goldannotator;
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;
        this.annotationmeasure = annotationmeasure;
        this.agreement = agreement;
    }

    getUUID(): string {
        return this.uuid;
    }
    getAnnotatorName(): string {
        return this.annotatorname;
    }

    getGoldAnnotator(): string {
        return this.goldannotator;
    }

    getOwnername(): string {
        return this.ownername;
    }

    getProjectname(): string {
        return this.projectname;
    }

    getPhasename(): string {
        return this.phasename
    }

    getAnnotationmeasure(): string {
        return this.annotationmeasure;
    }

    getAgreeement(): number {
        return this.agreement;
    }
    
    public static fromDto(dto: AnnotationStaticHistoryDto):AnnotationHistoryTable  {
        return new AnnotationHistoryTable(
            dto.uuid,
            dto.annotatorname,
            dto.goldannotator,
            dto.ownername,
            dto.projectname,
            dto.phasename,
            dto.annotationMeasure,
            dto.agreement
        );
    }

}