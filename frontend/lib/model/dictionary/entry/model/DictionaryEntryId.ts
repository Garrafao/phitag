import DictionaryEntryIdDto from "../dto/DictionaryEntryIdDto";

export default class DictionaryEntryId {

    readonly id: string;
    readonly dname: string;
    readonly uname: string;

    constructor(id: string, dname: string, uname: string) {
        this.id = id;
        this.dname = dname;
        this.uname = uname;
    }

    public copy(): DictionaryEntryId {
        return new DictionaryEntryId(
            this.id,
            this.dname,
            this.uname
        );
    }

    public shallowAnnonymizedCopy(): {
        id: string,
        dname: string,
        uname: string,
    } {
        return {
            id: this.id,
            dname: this.dname,
            uname: this.uname,
        };
    }

    static fromDto(dto: DictionaryEntryIdDto) {
        return new DictionaryEntryId(
            dto.id,
            dto.dname,
            dto.uname
        );
    }
}