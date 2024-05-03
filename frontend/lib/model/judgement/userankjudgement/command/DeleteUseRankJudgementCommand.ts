import IDeleteJudgementCommand from "../../command/IDeleteJudgementCommand";

export default class DeleteUseRankJudgementCommand implements IDeleteJudgementCommand {
    constructor(
        public readonly owner: string,
        public readonly project: string,
        public readonly phase: string,

        public readonly instance: string,
        public readonly annotator: string,

        public readonly UUID: string,
    ) {}
}