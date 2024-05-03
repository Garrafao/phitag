export default class CreateUserCommand {

    private readonly username: string;
    private readonly email: string;
    private readonly password: string;

    private readonly usecase: string;
    private readonly languages: Array<string>;

    private readonly privacypolicyAccepted: boolean;
    private readonly ofLegalAge: boolean;

    constructor(username: string, email: string, password: string, usecase: string, languages: Array<string>,
         privacypolicyAccepted: boolean, ofLegalAge: boolean) {
        this.username = username;
        this.email = email;
        this.password = password;

        this.usecase = usecase;
        this.languages = languages;
        this.privacypolicyAccepted = privacypolicyAccepted;
        this.ofLegalAge = ofLegalAge;
    }
}