import SelectableItem from "../../interfaces/selectableitem";
import AnnotationTypeDto from "../dto/AnnotationTypeDto";


export default class AnnotationType implements SelectableItem {
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

    static fromDto(dto: AnnotationTypeDto): AnnotationType {
        return new AnnotationType(dto.name, dto.visiblename);
    }

}