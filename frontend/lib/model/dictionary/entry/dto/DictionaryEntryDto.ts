import DictionaryEntrySenseDto from "../../sense/dto/DictionaryEntrySenseDto";
import DictionaryEntryIdDto from "./DictionaryEntryIdDto";

export default interface DictionaryEntryDto {

    readonly id: DictionaryEntryIdDto;

    readonly headword: string;
    readonly partofspeech: string;

    readonly senses: Array<DictionaryEntrySenseDto>;
}