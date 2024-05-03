export default class Guide {

    public id: string;
    public title: string;
    public priority: number;

    public description: string;
    public content: string;

    constructor(id: string, title: string, priority: number, description: string, content: string) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.description = description;
        this.content = content;
    }

}
