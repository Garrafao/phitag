import PhaseDto from "../../phase/dto/PhaseDto";
import StatusDto from "../../status/dto/StatusDto";

export default interface TaskDto {

    readonly botname: string;
    readonly taskowner: string;

    readonly phase: PhaseDto;
    readonly status: StatusDto;
}