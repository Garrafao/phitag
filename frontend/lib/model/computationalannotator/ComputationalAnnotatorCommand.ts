export default class ComputationalAnnotatorCommand {

    private readonly owner: string;
    private readonly project: string;
    private readonly phase: string;
    private readonly apiKey: string;
    private readonly temperature: number;
    private readonly topP: number;
    private readonly model: string;
    private readonly prompt: string;
    private readonly system: string;
    private readonly finalMessage: string;


    constructor(owner: string, project: string, phase: string, apiKey: string, temperature: number, topP: number, model: string, prompt: string, 
        system: string, finalMessage: string) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.apiKey = apiKey;
        this.temperature = temperature;
        this.topP = topP;
        this.model = model;
        this.prompt = prompt;
        this.system = system;
        this.finalMessage = finalMessage;
    }

}
