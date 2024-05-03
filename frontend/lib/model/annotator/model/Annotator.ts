import Entitlement from "../../entitlement/model/Entitlement";
import SelectableItem from "../../interfaces/selectableitem";
import User from "../../user/model/User";
import AnnotatorDto from "../dto/AnnotatorDto";
import AnnotatorId from "./AnnotatorId";


export default class Annotator implements SelectableItem {

    private readonly id: AnnotatorId;
    private readonly user: User;
    private readonly entitlement: Entitlement;

    private readonly isDummy: boolean;

    constructor(id: AnnotatorId, user: User, entitlement: Entitlement, isDummy: boolean = false) {
        this.id = id;
        this.user = user;
        this.entitlement = entitlement;
        this.isDummy = isDummy;
    }

    getId(): AnnotatorId {
        return this.id;
    }

    getName(): string {
        return this.user.getUsername();
    }

    getVisiblename(): string {
        return this.user.getDisplayname();
    }

    getUser(): User {
        return this.user;
    }

    getEntitlement(): Entitlement {
        return this.entitlement;
    }

    static fromDto(dto: AnnotatorDto): Annotator {
        return new Annotator(
            AnnotatorId.fromDto(dto.id),
            User.fromDto(dto.user),
            Entitlement.fromDto(dto.entitlement),
            false
        );
    }

}