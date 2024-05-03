export default class StartComputationalAnnotationCommand {
    private readonly owner: string;
    private readonly project: string;
    private readonly phase: string;

    private readonly annotators: string[];

    constructor (owner: string, project: string, phase: string, annotators: string[]) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;

        this.annotators = annotators;
    }
}