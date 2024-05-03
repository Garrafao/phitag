import JoblistingIdDto from "./JoblistingIdDto";

export default interface JoblistingDto {

    readonly id: JoblistingIdDto;

    readonly displayname: string;

    readonly open: boolean;
    readonly description: string;
}