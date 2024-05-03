package de.garrafao.phitag.domain.instance.wssimtag.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class TagidQueryComponent implements QueryComponent {

    private String tagid;

    public TagidQueryComponent(final String tagid) {
        this.tagid = tagid;
    }
    
}
