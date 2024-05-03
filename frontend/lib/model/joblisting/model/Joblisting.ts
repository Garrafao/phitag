import JoblistingDto from "../dto/JoblistingDto";
import JoblistingId from "./JoblistingId";

export default class Joblisting{

    private readonly id: JoblistingId;

    private readonly displayname: string;

    private readonly open: boolean;
    private readonly description: string;

    constructor(id: JoblistingId, displayname: string, open: boolean, description: string) {
        this.id = id;
        this.displayname = displayname;
        this.open = open;
        this.description = description;
    }

    public getId(): JoblistingId {
        return this.id;
    }

    public getDisplayname(): string {
        return this.displayname;
    }

    public isOpen(): boolean {
        return this.open;
    }

    public getDescription(): string {
        return this.description;
    }

    static fromDto(dto: JoblistingDto): Joblisting {
        return new Joblisting(JoblistingId.fromDto(dto.id), dto.displayname, dto.open, dto.description);
    }
}