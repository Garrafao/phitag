export default interface UserStatisticDto {
    readonly username: string;

    readonly projectcount: number;
    readonly languageCountMap: Map<string, number>;
    readonly annotationTypeCountMap: Map<string, number>;
    readonly pojectAnnotationCountMap: Map<string, number>;
}
