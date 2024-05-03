import EntitlementDto from "../../entitlement/dto/EntitlementDto";
import UserDto from "../../user/dto/UserDto";
import AnnotatorIdDto from "./AnnotatorIdDto";

export default interface AnnotatorDto {
    readonly id: AnnotatorIdDto;
    readonly user: UserDto;
    readonly entitlement: EntitlementDto;
}