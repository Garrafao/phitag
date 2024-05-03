import Phase from "../../phase/model/Phase";
import Status from "../../status/model/Status";
import TaskDto from "../dto/TaskDto";

export default class Task {

    private readonly botname: string;
    private readonly taskowner: string;

    private readonly phase: Phase;
    private readonly status: Status;

    constructor(botname: string, taskowner: string, phase: Phase, status: Status) {
        this.botname = botname;
        this.taskowner = taskowner;
        this.phase = phase;
        this.status = status;
    }

    getBotname(): string {
        return this.botname;
    }

    getTaskowner(): string {
        return this.taskowner;
    }

    getPhase(): Phase {
        return this.phase;
    }

    getStatus(): Status {
        return this.status;
    }

    static fromDto(dto: TaskDto): Task {
        return new Task(dto.botname,
            dto.taskowner,
            Phase.fromDto(dto.phase),
            Status.fromDto(dto.status));
    }

}