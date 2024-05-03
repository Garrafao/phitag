import GuidelineDto from "../dto/GuidelineDto";
import GuidelineId from "./GuidelineId";

export default class Guideline {

    private readonly id: GuidelineId;
    private readonly content: string;

    constructor(id: GuidelineId, content: string) {
        this.id = id;
        this.content = content;
    }

    public getId(): GuidelineId {
        return this.id;
    }

    public getContent(): string {
        return this.content;
    }

    static fromDto(dto: GuidelineDto): Guideline {
        return new Guideline(
            GuidelineId.fromDto(dto.id),
            dto.content
        );
    }

}