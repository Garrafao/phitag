import AnnotationType from "../../annotationtype/model/AnnotationType";
import Pair from "../../dummy/Pair";
import SelectableItem from "../../interfaces/selectableitem";
import Sampling from "../../sampling/model/Sampling";
import PhaseDto from "../dto/PhaseDto";
import PhaseId from "./PhaseId";

export default class Phase implements SelectableItem {

    private readonly id: PhaseId;

    private readonly displayname: string;

    private readonly tutorial: boolean;
    private readonly annotationType: AnnotationType;
    private readonly sampling: Sampling;

    private readonly description: string;
    private readonly taskhead: string;
    private readonly code: string;


    private readonly status: string;

    private readonly tutorialrequirements: Array<Pair<string, boolean>>;

    constructor(id: PhaseId, displayname: string, tutorial: boolean, annotationType: AnnotationType, sampling: Sampling, description: string,
        taskhead: string,  code: string, status: string, tutorialrequirements: Array<Pair<string, boolean>>) {
        this.id = id;

        this.displayname = displayname;

        this.tutorial = tutorial;

        this.annotationType = annotationType;
        this.sampling = sampling;

        this.description = description;
        this.taskhead = taskhead;
        this.code = code;


        this.status = status;

        this.tutorialrequirements = tutorialrequirements;
    }

    getName(): string {
        return this.id.getPhase();
    }
    getVisiblename(): string {
        return this.displayname;
    }

    getId(): PhaseId {
        return this.id;
    }

    getDisplayname(): string {
        return this.displayname;
    }

    isTutorial(): boolean {
        return this.tutorial;
    }

    getAnnotationType(): AnnotationType {
        return this.annotationType;
    }

    getSampling(): Sampling {
        return this.sampling;
    }

    getDescription(): string {
        return this.description;
    }
    getTaskHead(): string {
        return this.taskhead;
    }

    getCode(): string {
        return this.code;
    }
    
    getStatus(): string {
        return this.status;
    }

    getTutorialrequirements(): Array<Pair<string, boolean>> {
        return this.tutorialrequirements;
    }

    public static fromDto(dto: PhaseDto): Phase {
        return new Phase(
            PhaseId.fromDto(dto.id),
            dto.displayname,
            dto.tutorial,
            AnnotationType.fromDto(dto.annotationType),
            Sampling.fromDto(dto.sampling),
            dto.description,
            dto.taskhead,
            dto.code,
            dto.status,
            dto.tutorialrequirements
        );
    }

}