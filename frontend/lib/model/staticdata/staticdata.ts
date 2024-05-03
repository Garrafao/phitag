import { IStaticData } from "./istaticdata";

export default class StaticData implements IStaticData {

    id: string;
    title: string;
    description: string = "";

    imagepath: string;
    imagereversed: boolean;

    content: string;

    constructor(id: string, title: string, imagepath: string, imagereversed: boolean, description: string = "", content: string = "") {
        this.id = id;
        this.title = title;
        this.imagepath = imagepath;
        this.imagereversed = imagereversed;

        this.description = description;
        this.content = content;
    }
}
