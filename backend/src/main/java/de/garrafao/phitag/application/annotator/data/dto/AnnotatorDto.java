package de.garrafao.phitag.application.annotator.data.dto;

import de.garrafao.phitag.application.entitlement.data.EntitlementDto;
import de.garrafao.phitag.application.user.data.UserDto;
import de.garrafao.phitag.domain.annotator.Annotator;
import lombok.Getter;

@Getter
public class AnnotatorDto {
    
    private final AnnotatorIdDto id;
    private final UserDto user;
    private final EntitlementDto entitlement;

    private AnnotatorDto(
            final AnnotatorIdDto id,
            final UserDto user,
            final EntitlementDto entitlement) {
        this.id = id;
        this.user = user;
        this.entitlement = entitlement;
    }

    public static AnnotatorDto from(Annotator annotator) {
        return new AnnotatorDto(
                AnnotatorIdDto.from(annotator.getId()),
                UserDto.from(annotator.getUser()),
                EntitlementDto.from(annotator.getEntitlement()));
    }

}
