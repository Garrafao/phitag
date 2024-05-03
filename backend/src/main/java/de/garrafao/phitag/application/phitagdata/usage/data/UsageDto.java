package de.garrafao.phitag.application.phitagdata.usage.data;

import java.util.List;

import de.garrafao.phitag.domain.helper.Pair;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import lombok.Getter;

@Getter
public class UsageDto {

    private final UsageIdDto id;

    private final String context;
    private final List<Pair<Integer, Integer>> indexTargetToken;
    private final List<Pair<Integer, Integer>> indexTargetSentence;
    private final String lemma;
    private final String group;

    private UsageDto(
            final UsageIdDto id,
            final String context,
            final List<Pair<Integer, Integer>> indexTargetToken,
            final List<Pair<Integer, Integer>> indexTargetSentence,
            final String lemma,
            final String group) {
        this.id = id;

        this.context = context;
        this.indexTargetToken = indexTargetToken;
        this.indexTargetSentence = indexTargetSentence;
        this.lemma = lemma;
        this.group = group;
    }

    public static UsageDto from(final Usage usage) {
        return new UsageDto(
                UsageIdDto.from(usage.getId()),
                usage.getContext(),
                usage.getIndicesTargetToken(), 
                usage.getIndicesTargetSentence(),
                usage.getLemma(),
                usage.getGroup());
    }

}
