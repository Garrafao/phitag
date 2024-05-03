import JoblistingIdDto from "./JoblistingIdDto";

export default interface PersonalJoblistingDto {
    phase: string;
    
    readonly id: JoblistingIdDto;

    readonly displayname: string;

    readonly open: boolean;
    readonly description: string;

    readonly waitinglist: Array<string>;
}