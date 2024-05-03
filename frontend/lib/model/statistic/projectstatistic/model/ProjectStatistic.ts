import ProjectStatisticDto from "../dto/ProjectStatisticDto";

export default class ProjectStatistic {

    private readonly username: string;
    private readonly projectname: string;

    private readonly lemmacount: number;
    private readonly usagecount: number;
    private readonly usagesPerLemmaCountMap: Map<string, number>;

    constructor(username: string, projectname: string, lemmacount: number, usagecount: number, usagesPerLemmaCountMap: Map<string, number>) {
        this.username = username;
        this.projectname = projectname;
        this.lemmacount = lemmacount;
        this.usagecount = usagecount;
        this.usagesPerLemmaCountMap = new Map<string, number>(Object.entries(usagesPerLemmaCountMap));
    }

    public getUsername(): string {
        return this.username;
    }

    public getProjectname(): string {
        return this.projectname;
    }

    public getLemmacount(): number {
        return this.lemmacount;
    }

    public getUsagecount(): number {
        return this.usagecount;
    }

    public getUsagesPerLemmaCountMap(): Map<string, number> {
        return this.usagesPerLemmaCountMap;
    }

    public static fromDto(dto: ProjectStatisticDto): ProjectStatistic {
        return new ProjectStatistic(dto.username, dto.projectname, dto.lemmacount, dto.usagecount, dto.usagesPerLemmaCountMap);
    }
}