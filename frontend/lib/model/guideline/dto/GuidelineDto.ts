import GuidelineIdDto from "./GuidelineIdDto";

export default interface GuidelineDto {
    readonly id: GuidelineIdDto;
    readonly content: string;
}