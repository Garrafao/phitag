import ProjectDto from "../dto/ProjectDto";

import SelectableItem from "../../interfaces/selectableitem";

import ProjectId from "./ProjectId";
import Visibility from "../../visibility/model/Visibility";
import Language from "../../language/model/Language";

export default class Project implements SelectableItem {

    private readonly id: ProjectId;

    private readonly displayname: string;

    private readonly active: boolean;
    private readonly visibility: Visibility;
    
    private readonly language: Language;
    private readonly description: string;

    constructor(id: ProjectId, displayname: string, active: boolean, visibility: Visibility, language: Language, description: string) {
        this.id = id;

        this.displayname = displayname;

        this.active = active;
        this.visibility = visibility;

        this.language = language;
        this.description = description;
    }

    getName(): string {
        return this.id.getName();
    }

    getVisiblename(): string {
        return this.displayname;
    }

    getId(): ProjectId {
        return this.id;
    }

    getDisplayname(): string {
        return this.displayname;
    }

    isActive(): boolean {
        return this.active;
    }

    getVisibility(): Visibility {
        return this.visibility;
    }

    getLanguage(): Language {
        return this.language;
    }

    getDescription(): string {
        return this.description;
    }

    static fromDto(dto: ProjectDto): Project {
        return new Project(
            ProjectId.fromDto(dto.id),
            dto.displayname,
            dto.active,
            Visibility.fromDto(dto.visibility),
            Language.fromDto(dto.language),
            dto.description
        );
    }
}