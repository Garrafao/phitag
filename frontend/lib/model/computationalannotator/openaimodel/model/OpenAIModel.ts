import SelectableItem from "../../../interfaces/selectableitem";
import OpenAIModelDto from "../dto/OpenAIModelDto";


export default class OpenAIModel implements SelectableItem {
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

    static fromDto(dto: OpenAIModelDto): OpenAIModel{
        return new OpenAIModel(dto.name, dto.visiblename);
    }

}