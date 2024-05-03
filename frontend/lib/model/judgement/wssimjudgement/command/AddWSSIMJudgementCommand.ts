import IAddJudgementCommand from "../../command/IAddJudgementCommand";

export default class AddWSSIMJudmentCommand implements IAddJudgementCommand {

    readonly owner: string;
    readonly project: string;
    readonly phase: string;

    readonly instance: string;

    readonly label: string;
    readonly comment: string;

    constructor(owner: string, project: string, phase: string, instance: string, label: string, comment: string) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }
}