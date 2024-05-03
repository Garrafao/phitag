import DictionaryEntrySenseExampleDto from "../dto/DictionaryEntrySenseExampleDto";
import DictionaryEntrySenseExampleId from "./DictionaryEntrySenseExampleId";

export default class DictionaryEntrySenseExample {

    readonly id: DictionaryEntrySenseExampleId;

    readonly example: string;
    readonly order: number;

    constructor(id: DictionaryEntrySenseExampleId, example: string, order: number) {
        this.id = id;
        this.example = example;
        this.order = order;
    }

    public copy(): DictionaryEntrySenseExample {
        return new DictionaryEntrySenseExample(
            this.id.copy(),
            this.example,
            this.order
        );
    }

    public shallowAnnonymizedCopy(): {
        id: {
            id: string,
            senseId: string,
            entryId: string,
            dname: string,
            uname: string,
        },
        example: string,
        order: number,
    } {
        return {
            id: this.id.shallowAnnonymizedCopy(),
            example: this.example,
            order: this.order,
        };
    }

    public static fromDto(dto: DictionaryEntrySenseExampleDto) {
        return new DictionaryEntrySenseExample(
            DictionaryEntrySenseExampleId.fromDto(dto.id),
            dto.example,
            dto.order
        );
    }
}