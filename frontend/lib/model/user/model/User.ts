import UserDto from "../dto/UserDto";

import Language from "../../language/model/Language";
import AnnotationType from "../../annotationtype/model/AnnotationType";

export default class User {

    private readonly username: string;
    private readonly displayname: string;

    private readonly enabled: boolean;
    private readonly isbot: boolean;

    private readonly languages: Array<Language>;
    private readonly annotationTypes: Array<AnnotationType>;
    private readonly description: string;
    private readonly prolific_id: string;


    constructor(username: string,
        displayname: string,
        enabled: boolean,
        isbot: boolean,
        languages: Array<Language>,
        annotationTypes: Array<AnnotationType>,
        description: string,
        prolific_id: string) {
        this.username = username;
        this.displayname = displayname;

        this.enabled = enabled;
        this.isbot = isbot;

        this.languages = languages;
        this.languages.sort((a, b) => a.getName().localeCompare(b.getName()));
        this.annotationTypes = annotationTypes;
        this.annotationTypes.sort((a, b) => a.getName().localeCompare(b.getName()));

        this.description = description;
        this.prolific_id = prolific_id;
    }

    public getUsername(): string {
        return this.username;
    }

    public getDisplayname(): string {
        return this.displayname;
    }

    public isEnabled(): boolean {
        return this.enabled;
    }

    public isBot(): boolean {
        return this.isbot;
    }

    public getLanguages(): Array<Language> {
        return this.languages;
    }

    public getAnnotationTypes(): Array<AnnotationType> {
        return this.annotationTypes;
    }

    public getDescription(): string {
        return this.description;
    }
    public getProlificId():string {
        return this.prolific_id;
    }

    public static fromDto(dto: UserDto): User {
        return new User(
            dto.username,
            dto.displayname,
            dto.enabled,
            dto.isbot,
            dto.languages.map(language => Language.fromDto(language)),
            dto.annotationTypes.map(annotationType => AnnotationType.fromDto(annotationType)),
            dto.description,
            dto.prolific_id
        );
    }
}