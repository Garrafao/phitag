package de.garrafao.phitag.application.instance.wssimtag.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import lombok.Getter;

@Getter
public class WSSIMTagDto implements IInstanceDto {

    private final WSSIMTagIdDto id;

    private final String definition;
    private final String lemma;

    private WSSIMTagDto(final WSSIMTagIdDto id, final String definition, final String lemma) {
        this.id = id;
        this.definition = definition;
        this.lemma = lemma;
    }

    public static WSSIMTagDto from(final WSSIMTag wssimTag) {
        return new WSSIMTagDto(
                WSSIMTagIdDto.from(wssimTag.getId()),
                wssimTag.getDefinition(),
                wssimTag.getLemma());
    }

}
