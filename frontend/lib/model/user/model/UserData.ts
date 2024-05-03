import UserDataDto from "../dto/UserDataDto";

import Visibility from "../../visibility/model/Visibility";
import Language from "../../language/model/Language";
import Usecase from "../../usecase/model/Usecase";

export default class UserData {

    private readonly username: string;
    private readonly displayname: string;
    private readonly email: string;

    private readonly enabled: boolean;

    private readonly visibility: Visibility;
    private readonly usecase: Usecase;

    private readonly languages: Array<Language>;
    private readonly description: string;

    constructor(username: string = '', displayname: string = '', email: string = '', enabled: boolean = false,
        visibility: Visibility = new Visibility(), usecase: Usecase = new Usecase(),
        languages: Array<Language> = [new Language()], description: string = '') {
        this.username = username;
        this.displayname = displayname;
        this.email = email;

        this.enabled = enabled;

        this.visibility = visibility;
        this.usecase = usecase;

        this.languages = languages;
        this.languages.sort((a, b) => a.getName().localeCompare(b.getName()));
        this.description = description;
    }

    public getUsername(): string {
        return this.username;
    }

    public getDisplayname(): string {
        return this.displayname;
    }

    public getEmail(): string {
        return this.email;
    }

    public isEnabled(): boolean {
        return this.enabled;
    }

    public getVisibility(): Visibility {
        return this.visibility;
    }

    public getUsecase(): Usecase {
        return this.usecase;
    }

    public getLanguages(): Array<Language> {
        return this.languages;
    }

    public getDescription(): string {
        return this.description;
    }

    public static fromDto(dto: UserDataDto): UserData {
        return new UserData(dto.username, dto.displayname, dto.email, dto.enabled, Visibility.fromDto(dto.visibility), Usecase.fromDto(dto.usecase), dto.languages.map(Language.fromDto), dto.description);
    }
}