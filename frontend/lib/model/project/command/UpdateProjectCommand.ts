export default class UpdateProjectCommand{

    private readonly newname: string;
 
    private readonly newdescription: string;
    private readonly visibility: string;

    private readonly language: string;
    private readonly active: boolean;


    constructor(newname: string, newdescription: string, visibility :string, language: string, active: boolean) {
        this.newname = newname;
 
        this.newdescription = newdescription;

        this.visibility = visibility;

        this.language = language;

        this.active = active;
    }

}
