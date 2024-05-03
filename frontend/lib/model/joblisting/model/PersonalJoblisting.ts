import DummySelectable from "../../dummy/DummySelectable";
import PersonalJoblistingDto from "../dto/PersonalJoblistingDto";
import JoblistingId from "./JoblistingId";


export default class PersonalJoblisting {

    private readonly id: JoblistingId;

    private readonly displayname: string;
    private readonly phase: string;

    private readonly open: boolean;
    private readonly description: string;

    private readonly waitinglist: Array<DummySelectable>;

    constructor(id: JoblistingId, displayname: string, phase: string, open: boolean, description: string, waitinglist: Array<DummySelectable>) {
        this.id = id;

        this.displayname = displayname;
        this.phase = phase;
        this.open = open;
        this.description = description;

        this.waitinglist = waitinglist;
    }

    public getId(): JoblistingId {
        return this.id;
    }

    public getDisplayname(): string {
        return this.displayname;
    }
    public getPhase(): string {
        return this.phase;
    }


    public isOpen(): boolean {
        return this.open;
    }

    public getDescription(): string {
        return this.description;
    }

    public getWaitinglist(): Array<DummySelectable> {
        return this.waitinglist;
    }

    static fromDto(dto: PersonalJoblistingDto): PersonalJoblisting {
        const waitinglist = new Array<DummySelectable>();
        dto.waitinglist.forEach((element: string) => waitinglist.push(new DummySelectable(element, element)));
        return new PersonalJoblisting(JoblistingId.fromDto(dto.id), dto.displayname,dto.phase, dto.open, dto.description, waitinglist);
    }


}