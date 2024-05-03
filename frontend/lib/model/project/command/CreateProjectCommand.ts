export default class CreateProjectCommand {

    private readonly name: string;
    private readonly visibility: string;
    private readonly language: string;
    private readonly description: string;

    constructor(name: string, visibility: string, language: string, description: string) {
        this.name = name;

        this.visibility = visibility;

        this.language = language;
        this.description = description;
    }

}
