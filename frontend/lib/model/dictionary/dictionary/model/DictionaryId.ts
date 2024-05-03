import DictionaryIdDto from "../dto/DictionaryIdDto";

export default class DictionaryId {

    readonly dname: string;
    readonly uname: string;

    constructor(dname: string, uname: string) {
        this.dname = dname;
        this.uname = uname;
    }

    static fromDto(dto: DictionaryIdDto) {
        return new DictionaryId(
            dto.dname,
            dto.uname
        );
    }

}