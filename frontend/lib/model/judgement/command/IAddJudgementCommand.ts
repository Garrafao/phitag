export default interface IAddJudgementCommand {
    readonly owner: string;
    readonly project: string;
    readonly phase: string;
    readonly instance: string;
}