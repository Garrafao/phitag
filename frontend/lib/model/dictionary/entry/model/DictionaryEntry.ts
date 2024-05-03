import DictionaryEntrySense from "../../sense/model/DictionaryEntrySense";
import DictionaryEntryDto from "../dto/DictionaryEntryDto";
import DictionaryEntryId from "./DictionaryEntryId";

export default class DictionaryEntry {

    readonly id: DictionaryEntryId;

    headword: string;
    partofspeech: string;

    readonly senses: Array<DictionaryEntrySense>;

    constructor(id: DictionaryEntryId, headword: string, partofspeech: string, senses: Array<DictionaryEntrySense>) {
        this.id = id;
        this.headword = headword;
        this.partofspeech = partofspeech;
        this.senses = senses;
    }

    public copy(): DictionaryEntry {
        return new DictionaryEntry(
            this.id.copy(),
            this.headword,
            this.partofspeech,
            this.senses.map(sense => sense.copy())
        );
    }

    public shallowAnnonymizedCopy(): {
        id: {
            id: string,
            dname: string,
            uname: string,
        },
        headword: string,
        partofspeech: string,
    } {
        return {
            id: this.id.shallowAnnonymizedCopy(),
            headword: this.headword,
            partofspeech: this.partofspeech,
        };
    }

    public static fromDto(dto: DictionaryEntryDto) {
        return new DictionaryEntry(
            DictionaryEntryId.fromDto(dto.id),
            dto.headword,
            dto.partofspeech,
            dto.senses.map(DictionaryEntrySense.fromDto)
        );
    }

}