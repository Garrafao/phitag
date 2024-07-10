export default class RemoveAnnotatorCommand {
    private readonly username: string;
    private readonly usernameProjectname: string;

    constructor(username: string, usernameProjectname: string) {
        this.username = username;
        this.usernameProjectname = usernameProjectname;
    }
    
}