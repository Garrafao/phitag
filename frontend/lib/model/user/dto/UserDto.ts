import AnnotationTypeDto from "../../annotationtype/dto/AnnotationTypeDto";
import LanguageDto from "../../language/dto/LanguageDto";

export default interface UserDto {
    readonly username: string;
    readonly displayname: string;
    
    readonly enabled: boolean;
    readonly isbot: boolean;

    readonly languages: Array<LanguageDto>;
    readonly annotationTypes: Array<AnnotationTypeDto>;
    readonly description: string;
    readonly prolific_id: string;

}