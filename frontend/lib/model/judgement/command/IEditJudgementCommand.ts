export default interface IEditJudgementCommand {

    readonly owner: string;
    readonly project: string;
    readonly phase: string;

    readonly instance: string;
    readonly annotator: string;

    readonly UUID: string;

    readonly label: string;
    readonly comment: string;
}