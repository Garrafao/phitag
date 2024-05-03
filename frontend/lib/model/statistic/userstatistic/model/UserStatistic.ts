import UserStatisticDto from "../dto/UserStatisticDto";

export default class UserStatistic {

    private readonly username: string;

    private readonly projectcount: number;
    private readonly languageCountMap: Map<string, number>;
    private readonly annotationTypeCountMap: Map<string, number>;
    private readonly pojectAnnotationCountMap: Map<string, number>;

    constructor(username: string, projectcount: number, languageCountMap: Map<string, number>, annotationTypeCountMap: Map<string, number>, pojectAnnotationCountMap: Map<string, number>) {
        this.username = username;
        this.projectcount = projectcount;
        this.languageCountMap = new Map<string, number>(Object.entries(languageCountMap));
        this.annotationTypeCountMap = new Map<string, number>(Object.entries(annotationTypeCountMap));
        this.pojectAnnotationCountMap = new Map<string, number>(Object.entries(pojectAnnotationCountMap));
    }

    public getUsername(): string {
        return this.username;
    }

    public getProjectcount(): number {
        return this.projectcount;
    }

    public getLanguageCountMap(): Map<string, number> {
        return this.languageCountMap;
    }

    public getAnnotationTypeCountMap(): Map<string, number> {
        return this.annotationTypeCountMap;
    }

    public getPojectAnnotationCountMap(): Map<string, number> {
        return this.pojectAnnotationCountMap;
    }

    public static fromDto(dto: UserStatisticDto): UserStatistic {
        return new UserStatistic(dto.username, dto.projectcount, dto.languageCountMap, dto.annotationTypeCountMap, dto.pojectAnnotationCountMap);
    }
}