export default class RemoveAnnotatorCommand {
    private readonly username: string;
    private readonly project: string;
    private readonly owner: string;


    constructor(username: string, project: string, owner: string) {
        this.username = username;
        this.project = project;
        this.owner = owner;
    }
    
}