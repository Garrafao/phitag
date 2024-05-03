import DictionaryEntrySenseExampleDto from "../../example/dto/DictionaryEntrySenseExampleDto";
import DictionaryEntrySenseIdDto from "./DictionaryEntrySenseIdDto";

export default interface DictionaryEntrySenseDto {
    readonly id: DictionaryEntrySenseIdDto;

    readonly definition: string;
    readonly order: number;

    readonly examples: Array<DictionaryEntrySenseExampleDto>;
}