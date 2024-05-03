import SelectableItem from "../../interfaces/selectableitem";
import StatusDto from "../dto/StatusDto";

export default class Status implements SelectableItem {
    private readonly name: string;
    private readonly visiblename: string;

    constructor(name: string = '', visiblename: string = '') {
        this.name = name;
        this.visiblename = visiblename;
    }

    getName(): string {
        return this.name;
    }

    getVisiblename(): string {
        return this.visiblename;
    }

    static fromDto(dto: StatusDto): Status {
        return new Status(dto.name, dto.visiblename);
    }

}