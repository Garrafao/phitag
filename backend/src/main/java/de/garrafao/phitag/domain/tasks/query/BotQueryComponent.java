package de.garrafao.phitag.domain.tasks.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class BotQueryComponent implements QueryComponent {

    private final String botname;

    public BotQueryComponent(final String botname) {
        this.botname = botname;
    }
    
}
