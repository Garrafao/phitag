package de.garrafao.phitag.domain.user.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class IsBotQueryComponent implements QueryComponent {
    
    private final Boolean isBot;

    public IsBotQueryComponent(Boolean isBot) {
        this.isBot = isBot;
    }
}
