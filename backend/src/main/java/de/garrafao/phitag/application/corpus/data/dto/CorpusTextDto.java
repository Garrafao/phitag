package de.garrafao.phitag.application.corpus.data.dto;

import de.garrafao.phitag.domain.corpustext.CorpusText;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CorpusTextDto {

    private final String id;

    private final String text;
    private final String orthography;

    @Setter
    private CorpusTextSmallDto previous;
    @Setter
    private CorpusTextSmallDto next;

    private final CorpusInformationDto corpusInformation;

    private CorpusTextDto(final String id, final String text, final String orthography,
            final CorpusTextSmallDto previous, final CorpusTextSmallDto next,
            final CorpusInformationDto corpusInformation) {
        this.id = id;
        this.text = text;
        this.orthography = orthography;
        this.previous = previous;
        this.next = next;
        this.corpusInformation = corpusInformation;
    }

    public static CorpusTextDto from(final CorpusText corpusText) {
        return new CorpusTextDto(
                corpusText.getId(),
                corpusText.getText(),
                corpusText.getOrthography(),
                CorpusTextSmallDto.from(corpusText.getPreviousid()),
                CorpusTextSmallDto.from(corpusText.getNextid()),
                CorpusInformationDto.from(corpusText.getCorpusInformation()));
    }

    public static CorpusTextDto from(final CorpusText corpusText, final CorpusText previous,
            final CorpusText next) {
        return new CorpusTextDto(
                corpusText.getId(),
                corpusText.getText(),
                corpusText.getOrthography(),
                CorpusTextSmallDto.from(previous),
                CorpusTextSmallDto.from(next),
                CorpusInformationDto.from(corpusText.getCorpusInformation()));
    }

}
