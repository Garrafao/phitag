import SelectableItem from "../../interfaces/selectableitem";
import UsecaseDto from "../dto/UsecaseDto";

export default class Usecase implements SelectableItem {
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

    static fromDto(dto: UsecaseDto): Usecase {
        return new Usecase(dto.name, dto.visiblename);
    }

}