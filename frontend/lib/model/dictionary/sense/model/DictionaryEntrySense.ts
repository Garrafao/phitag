import DictionaryEntrySenseExample from "../../example/model/DictionaryEntrySenseExample";
import DictionaryEntrySenseDto from "../dto/DictionaryEntrySenseDto";
import DictionaryEntrySenseId from "./DictionaryEntrySenseId";

export default class DictionaryEntrySense {

    readonly id: DictionaryEntrySenseId;

    readonly definition: string;
    readonly order: number;

    readonly examples: Array<DictionaryEntrySenseExample>;

    constructor(id: DictionaryEntrySenseId, definition: string, order: number, examples: Array<DictionaryEntrySenseExample>) {
        this.id = id;
        this.definition = definition;
        this.order = order;
        this.examples = examples;
    }

    public copy(): DictionaryEntrySense {
        return new DictionaryEntrySense(
            this.id.copy(),
            this.definition,
            this.order,
            this.examples.map(example => example.copy())
        );
    }

    public shallowAnnonymizedCopy(): {
        id: {
            id: string,
            entryId: string,
            dname: string,
            uname: string,
        },
        definition: string,
        order: number,
    } {
        return {
            id: this.id.shallowAnnonymizedCopy(),
            definition: this.definition,
            order: this.order,
        };
    }

    public static fromDto(dto: DictionaryEntrySenseDto) {
        return new DictionaryEntrySense(
            DictionaryEntrySenseId.fromDto(dto.id),
            dto.definition,
            dto.order,
            dto.examples.map(DictionaryEntrySenseExample.fromDto)
        );
    }

}
