import SelectableItem from "../../interfaces/selectableitem";
import VisibilityDto from "../dto/VisibilityDto";

export default class Visibility implements SelectableItem {
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

    static fromDto(dto: VisibilityDto): Visibility {
        return new Visibility(dto.name, dto.visiblename);
    }

}