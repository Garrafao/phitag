export default class GuideHeader {

    public id: string;
    public title: string;
    public priority: number;

    public description: string;

    constructor(id: string, title: string, priority: number, description: string) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.description = description;
    }

}
