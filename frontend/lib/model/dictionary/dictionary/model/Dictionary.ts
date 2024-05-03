import DictionaryDto from "../dto/DictionaryDto";
import DictionaryId from "./DictionaryId";

export default class Dictionary {

    readonly id: DictionaryId;

    readonly description: string;

    constructor(id: DictionaryId, description: string) {
        this.id = id;
        this.description = description;
    }

    static fromDto(dto: DictionaryDto) {
        return new Dictionary(
            DictionaryId.fromDto(dto.id),
            dto.description
        );
    }

}