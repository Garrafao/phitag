import IInstanceDto from "../../dto/IInstanceDto";
import WSSIMTagIdDto from "./WSSIMTagIdDto";

export default interface WSSIMTagDto extends IInstanceDto {

    readonly id: WSSIMTagIdDto;

    readonly definition: string;
    readonly lemma: string;
}