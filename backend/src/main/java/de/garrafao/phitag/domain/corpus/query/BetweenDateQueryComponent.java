package de.garrafao.phitag.domain.corpus.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import de.garrafao.phitag.domain.helper.Pair;
import lombok.Getter;

@Getter
public class BetweenDateQueryComponent implements QueryComponent {

    private final Pair<Integer, Integer> dateRange;

    public BetweenDateQueryComponent(final Integer from, final Integer to) {
        this.dateRange = new Pair<>(from, to);
    }
    
}
