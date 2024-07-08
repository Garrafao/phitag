export default class EditUsageCommand {
    constructor(
        public readonly dataid: string,
        public readonly project: string,
        public readonly owner: string
    ) {}
}