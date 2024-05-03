import SelectableItem from "../../interfaces/selectableitem";
import EntitlementDto from "../dto/EntitlementDto";

export default class Entitlement implements SelectableItem {
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

    static fromDto(dto: EntitlementDto): Entitlement {
        return new Entitlement(dto.name, dto.visiblename);
    }
}