export default interface IDeleteJudgementCommand {
    readonly owner: string;
    readonly project: string;
    readonly phase: string;

    readonly instance: string;
    readonly annotator: string;

    readonly UUID: string;
}