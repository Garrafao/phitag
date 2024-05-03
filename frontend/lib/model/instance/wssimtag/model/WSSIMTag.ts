import IInstanceDto from "../../dto/IInstanceDto";
import { IInstance, IInstanceConstructor } from "../../model/IInstance";
import WSSIMTagDto from "../dto/WSSIMTagDto";
import WSSIMTagId from "./WSSIMTagId";

export default class WSSIMTag implements IInstance {

    readonly id: WSSIMTagId;

    readonly definition: string;
    readonly lemma: string;

    constructor(id: WSSIMTagId, definition: string, lemma: string) {
        this.id = id;
        this.definition = definition;
        this.lemma = lemma;
    }

    public getId(): WSSIMTagId {
        return this.id;
    }

    public getDefinition(): string {
        return this.definition;
    }

    public getLemma(): string {
        return this.lemma;
    }

    public static fromDto(dto: WSSIMTagDto): WSSIMTag {
        return new WSSIMTag(
            WSSIMTagId.fromDto(dto.id),
            dto.definition,
            dto.lemma
        );
    }
}

export class WSSIMTagConstructor implements IInstanceConstructor {
    fromDto(dto: WSSIMTagDto): WSSIMTag {
        return WSSIMTag.fromDto(dto);
    }
}