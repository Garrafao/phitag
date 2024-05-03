import SelectableItem from "../../interfaces/selectableitem";
import SamplingDto from "../dto/SamplingDto";

export default class Sampling implements SelectableItem {
    private readonly name: string;
    private readonly visiblename: string;

    constructor(name: string, visiblename: string) {
        this.name = name;
        this.visiblename = visiblename;
    }

    getName(): string {
        return this.name;
    }

    getVisiblename(): string {
        return this.visiblename;
    }

    static fromDto(dto: SamplingDto): Sampling {
        return new Sampling(dto.name, dto.visiblename);
    }
}