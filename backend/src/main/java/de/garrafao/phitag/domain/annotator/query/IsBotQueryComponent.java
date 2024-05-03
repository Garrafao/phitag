package de.garrafao.phitag.domain.annotator.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class IsBotQueryComponent implements QueryComponent {

    private final boolean isBot;

    public IsBotQueryComponent(final boolean isBot) {
        this.isBot = isBot;
    }
    
}
