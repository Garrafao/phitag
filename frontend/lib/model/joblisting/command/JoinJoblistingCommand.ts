export default class JoinJoblistingCommand {
    private readonly name: string;
    private readonly owner: string;
    private readonly project: string;

    constructor(name: string, owner: string, project: string) {
        this.name = name;
        this.owner = owner;
        this.project = project;
    }
}