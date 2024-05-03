import DictionaryIdDto from "./DictionaryIdDto";

export default interface DictionaryDto {
    readonly id: DictionaryIdDto;
    readonly description: string;
}