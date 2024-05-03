export default class CreatePhaseCommand {

    private readonly name: string;
    private readonly owner: string;
    private readonly project: string;

    private readonly annotationType: string;
    private readonly sampling: string;

    private readonly tutorial: boolean;
    private readonly annotationAgreement: string;
    private readonly threshold: number;

    private readonly description: string;
    private readonly taskhead: string;
    private readonly instancePerSample: number;


    constructor (
        name: string, owner: string, project: string, 
        annotationType: string, sampling: string, 
        tutorial: boolean, annotationAgreement: string, threshold: number,
        description: string,
        taskhead: string,
        instancePerSample: number) {
        this.name = name;
        this.owner = owner;
        this.project = project;
        
        this.annotationType = annotationType;
        this.sampling = sampling;

        this.tutorial = tutorial;
        this.annotationAgreement = annotationAgreement;
        this.threshold = threshold;

        this.description = description;
        this.taskhead = taskhead;
        this.instancePerSample = instancePerSample;
    }


}