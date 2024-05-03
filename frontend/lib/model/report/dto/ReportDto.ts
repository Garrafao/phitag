import StatusDto from "../../status/dto/StatusDto";

export default interface ReportDto {
    readonly id: string;

    readonly user: string;
    readonly status: StatusDto;
    readonly description: string;
}