import IInstanceIdDto from "../../dto/IInstanceIdDto";

export default interface sentimentInstanceIdDto extends IInstanceIdDto {
    readonly instanceId: string;

    readonly phase: string;
    readonly project: string;
    readonly owner: string;
}
