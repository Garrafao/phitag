export default class AddUsersFromWaitinglistCommand {
    private readonly name: string;
    private readonly owner: string;
    private readonly project: string;

    private readonly users: Array<string>;

    constructor(name: string, owner: string, project: string, users: Array<string>) {
        this.name = name;
        this.owner = owner;
        this.project = project;

        this.users = users;
    }
}