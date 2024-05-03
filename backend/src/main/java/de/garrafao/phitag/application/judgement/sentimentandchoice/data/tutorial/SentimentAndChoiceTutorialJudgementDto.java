package de.garrafao.phitag.application.judgement.sentimentandchoice.data.tutorial;

import de.garrafao.phitag.application.instance.sentimentandchoice.data.SentimentAndChoiceInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.tutorial.sentimentandchoice.SentimentAndChoiceTutorialJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class SentimentAndChoiceTutorialJudgementDto implements IJudgementDto {

    private final SentimentAndChoiceTutorialJudgementIdDto id;

    private final SentimentAndChoiceInstanceDto instance;

    private final String label;
    private final String comment;

    private SentimentAndChoiceTutorialJudgementDto(
            final SentimentAndChoiceTutorialJudgementIdDto id,
            final SentimentAndChoiceInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static SentimentAndChoiceTutorialJudgementDto from(@NonNull final SentimentAndChoiceTutorialJudgement judgement) {
        return new SentimentAndChoiceTutorialJudgementDto(
                SentimentAndChoiceTutorialJudgementIdDto.from(judgement.getId()),
                SentimentAndChoiceInstanceDto.from(judgement.getInstance()),
                judgement.getLabel(),
                judgement.getComment());
    }
}
