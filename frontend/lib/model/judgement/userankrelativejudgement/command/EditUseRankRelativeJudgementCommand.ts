import IEditJudgementCommand from "../../command/IEditJudgementCommand";

export default class EditUseRankRelativeJudgementCommand implements IEditJudgementCommand {

    public readonly owner: string;
    public readonly project: string;
    public readonly phase: string;

    public readonly instance: string;
    public readonly annotator: string;

    public readonly UUID: string;

    public readonly label: string;
    public readonly comment: string;

    constructor(owner: string, project: string, phase: string, instance: string, annotator: string, UUID: string, label: string, comment: string) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.instance = instance;
        this.annotator = annotator;
        this.UUID = UUID;
        this.label = label;
        this.comment = comment;
    }


}