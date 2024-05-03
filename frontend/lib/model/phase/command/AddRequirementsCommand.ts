export default class AddRequirementsCommand {
    private readonly owner: string;
    private readonly project: string;
    private readonly phase: string;

    private readonly tutorials: string[];

    constructor(owner: string, project: string, phase: string, tutorials: string[]) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.tutorials = tutorials;
    }
}