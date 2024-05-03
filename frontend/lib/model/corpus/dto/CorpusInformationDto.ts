export default interface CorpusInformationDto {
    readonly id: string;

    readonly title: string;
    readonly author: string;
    readonly date: number;
    readonly language: string;
    readonly resource: string;

    readonly corpusnameFull: string;
    readonly corpusnameShort: string;

}