export default class EditAnnotatorCommand {
    private readonly owner: string;
    private readonly project: string;

    private readonly annotator: string;
    private readonly entitlement: string;

    constructor(owner: string, project: string, annotator: string, entitlement: string) {
        this.owner = owner;
        this.project = project;
        this.annotator = annotator;
        this.entitlement = entitlement;
    }
    
}