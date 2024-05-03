export default class EditUsageCommand {
    constructor(
        public readonly dataid: string,
        public readonly owner: string,
        public readonly project: string,

        public readonly context: string,

        public readonly indexTargetToken: string,
        public readonly indexTargetSentence: string,

        public readonly lemma: string,
        public readonly group: string,
    ) {}
}