import SelectableItem from "../interfaces/selectableitem";

export default class DummySelectable implements SelectableItem {
    
    private readonly name: string;
    private readonly visiblename: string;
    
    constructor(name: string, visiblename: string = name) {
        this.name = name;
        this.visiblename = visiblename;
    }

    getName(): string {
        return this.name;
    }

    getVisiblename(): string {
        return this.visiblename;
    }

    public static fromArray(array: string[]): DummySelectable[] {
        return array.map((item) => new DummySelectable(item));
    }

} 