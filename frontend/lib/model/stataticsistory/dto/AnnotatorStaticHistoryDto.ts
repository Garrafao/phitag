
export default interface AnnotationStaticHistoryDto {

    readonly uuid: string ;

    readonly annotatorname: string;

    readonly goldannotator: string;
    readonly ownername: string;
    readonly projectname: string;

    readonly phasename: string;
    readonly annotationMeasure: string;

    readonly agreement: number;

}