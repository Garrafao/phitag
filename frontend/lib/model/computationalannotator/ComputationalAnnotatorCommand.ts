export default class ComputationalAnnotatorCommand {

    private readonly owner: string;
    private readonly project: string;
    private readonly phase: string;
    private readonly apiKey: string;
    private readonly model: string;
    private readonly prompt: string;

    constructor(owner: string, project: string, phase: string, apiKey: string, model: string, prompt: string) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.apiKey = apiKey;
        this.model = model;
        this.prompt = prompt;
    }

}
