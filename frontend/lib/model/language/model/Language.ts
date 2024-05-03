import SelectableItem from "../../interfaces/selectableitem";
import LanguageDto from "../dto/LanguageDto";


export default class Language implements SelectableItem {
    private readonly name: string;

    constructor(name: string = '') {
        this.name = name;
    }

    getName(): string {
        return this.name;
    }

    getVisiblename(): string {
        return this.name;
    }

    static fromDto(dto: LanguageDto): Language {
        return new Language(dto.name);
    }

}