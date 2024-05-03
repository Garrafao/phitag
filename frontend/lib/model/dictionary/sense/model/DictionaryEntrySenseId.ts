import DictionaryEntrySenseIdDto from "../dto/DictionaryEntrySenseIdDto";

export default class DictionaryEntrySenseId {
    readonly id: string;
    readonly entryId: string;
    readonly dname: string;
    readonly uname: string;

    constructor(id: string, entryId: string, dname: string, uname: string) {
        this.id = id;
        this.entryId = entryId;
        this.dname = dname;
        this.uname = uname;
    }

    public copy(): DictionaryEntrySenseId {
        return new DictionaryEntrySenseId(
            this.id,
            this.entryId,
            this.dname,
            this.uname
        );
    }

    public shallowAnnonymizedCopy(): {
        id: string,
        entryId: string,
        dname: string,
        uname: string,
    } {
        return {
            id: this.id,
            entryId: this.entryId,
            dname: this.dname,
            uname: this.uname,
        };
    }

    static fromDto(dto: DictionaryEntrySenseIdDto) {
        return new DictionaryEntrySenseId(
            dto.id,
            dto.entryId,
            dto.dname,
            dto.uname
        );
    }
}
