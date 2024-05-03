export default class CreateAnnotatorCommand {
    
    constructor(
        private readonly owner: string,
        private readonly project: string,

        private readonly username: string,
        private readonly entitlement: string,
    ) {}
}