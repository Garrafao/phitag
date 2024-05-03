package de.garrafao.phitag.application.judgement.sentimentandchoice.data;

import de.garrafao.phitag.application.instance.sentimentandchoice.data.SentimentAndChoiceInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class SentimentAndChoiceJudgementDto implements IJudgementDto {

    private final SentimentAndChoiceJudgementIdDto id;

    private final SentimentAndChoiceInstanceDto instance;

    private final String label;
    private final String comment;

    private SentimentAndChoiceJudgementDto(
            final SentimentAndChoiceJudgementIdDto id,
            final SentimentAndChoiceInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static SentimentAndChoiceJudgementDto from(@NonNull final SentimentAndChoiceJudgement judgement) {
        return new SentimentAndChoiceJudgementDto(
                SentimentAndChoiceJudgementIdDto.from(judgement.getId()),
                SentimentAndChoiceInstanceDto.from(judgement.getInstance()),
                judgement.getLabel(),
                judgement.getComment());
    }
}
