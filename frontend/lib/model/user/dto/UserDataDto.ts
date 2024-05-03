import LanguageDto from "../../language/dto/LanguageDto";
import UsecaseDto from "../../usecase/dto/UsecaseDto";
import VisibilityDto from "../../visibility/dto/VisibilityDto";

export default interface UserDataDto {
    readonly username: string;
    readonly displayname: string;
    readonly email: string;
    
    readonly enabled: boolean;
    
    readonly visibility: VisibilityDto;
    readonly usecase: UsecaseDto;
    
    readonly languages: Array<LanguageDto>;
    readonly description: string;
    readonly prolific_id: string;
}
