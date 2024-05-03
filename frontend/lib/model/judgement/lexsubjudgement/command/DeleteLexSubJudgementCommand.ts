import IDeleteJudgementCommand from "../../command/IDeleteJudgementCommand";

export default class DeleteLexSubJudgementCommand implements IDeleteJudgementCommand {

    public readonly owner: string;
    public readonly project: string;
    public readonly phase: string;

    public readonly instance: string;
    public readonly annotator: string;

    public readonly UUID: string;

    constructor(owner: string, project: string, phase: string, instance: string, annotator: string, UUID: string) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;

        this.instance = instance;
        this.annotator = annotator;

        this.UUID = UUID;
    }

}