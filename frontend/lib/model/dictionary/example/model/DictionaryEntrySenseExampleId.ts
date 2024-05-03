import DictionaryEntrySenseExampleIdDto from "../dto/DictionaryEntrySenseExampleIdDto";

export default class DictionaryEntrySenseExampleId {

    readonly id: string;
    readonly senseId: string;
    readonly entryId: string;
    readonly dname: string;
    readonly uname: string;

    constructor(id: string, senseId: string, entryId: string, dname: string, uname: string) {
        this.id = id;
        this.senseId = senseId;
        this.entryId = entryId;
        this.dname = dname;
        this.uname = uname;
    }

    public copy(): DictionaryEntrySenseExampleId {
        return new DictionaryEntrySenseExampleId(
            this.id,
            this.senseId,
            this.entryId,
            this.dname,
            this.uname
        );
    }

    shallowAnnonymizedCopy(): { id: string; senseId: string; entryId: string; dname: string; uname: string; } {
        return {
            id: this.id,
            senseId: this.senseId,
            entryId: this.entryId,
            dname: this.dname,
            uname: this.uname,
        };
    }

    static fromDto(dto: DictionaryEntrySenseExampleIdDto) {
        return new DictionaryEntrySenseExampleId(
            dto.id,
            dto.senseId,
            dto.entryId,
            dto.dname,
            dto.uname
        );
    }
}