export default interface ProjectStatisticDto {
    readonly username: string;
    readonly projectname: string;

    readonly lemmacount: number;
    readonly usagecount: number;
    readonly usagesPerLemmaCountMap: Map<string, number>;
}
