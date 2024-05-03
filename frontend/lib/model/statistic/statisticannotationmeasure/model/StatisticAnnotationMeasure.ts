import SelectableItem from "../../../interfaces/selectableitem";
import StatisticAnnotationMeasureDto from "../dto/StatisticAnnotationMeasureDto";

export default class StatisticAnnotationMeasure implements SelectableItem {

    private readonly id: string;
    private readonly name: string;

    constructor(id: string = '', name: string = '') {
        this.id = id;
        this.name = name;
    }

    getVisiblename(): string {
        return this.name;
    }

    getId(): string {
        return this.id;
    }

    getName(): string {
        return this.name;
    }

    static fromDto(dto: StatisticAnnotationMeasureDto): StatisticAnnotationMeasure {
        return new StatisticAnnotationMeasure(dto.id, dto.name);
    }

}