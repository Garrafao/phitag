export default class RemoveAnnotatorCommand {
    private readonly username: string;
    private readonly project: string;
    private readonly owner: string;
    private readonly annotator: string;
    private readonly entitlement: string;



    constructor(username: string, project: string, owner: string, annotator: string, entitlement: string) {
        this.username = username;
        this.project= project;
        this.owner = owner;
        this.annotator = annotator;
        this.entitlement = entitlement;
    }
    
}