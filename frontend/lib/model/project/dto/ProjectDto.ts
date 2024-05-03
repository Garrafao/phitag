import ProjectIdDto from "./ProjectIdDto";
import LanguageDto from "../../language/dto/LanguageDto";
import VisibilityDto from "../../visibility/dto/VisibilityDto";

export default interface ProjectDto {
    readonly id: ProjectIdDto;

    readonly displayname: string;

    readonly active: boolean;
    readonly visibility: VisibilityDto;

    readonly language: LanguageDto;
    readonly description: string;
}