export default interface UsePairTutorialData{
    map(arg0: (item: any) => string): unknown;
    length: number;

    readonly firstUsage: string;
    readonly secondUsage: string;

    readonly lemma: string;
    readonly judgement: string;


  
}