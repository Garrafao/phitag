import DictionaryEntrySenseExampleIdDto from "./DictionaryEntrySenseExampleIdDto";

export default interface DictionaryEntrySenseExampleDto {
    readonly id: DictionaryEntrySenseExampleIdDto;

    readonly example: string;
    readonly order: number;
}