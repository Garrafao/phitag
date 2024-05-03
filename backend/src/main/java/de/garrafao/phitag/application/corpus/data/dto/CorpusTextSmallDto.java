package de.garrafao.phitag.application.corpus.data.dto;

import de.garrafao.phitag.domain.corpustext.CorpusText;
import lombok.Getter;

@Getter
public class CorpusTextSmallDto {

    private final String id;

    private final String text;
    private final String orthography;

    private CorpusTextSmallDto(final String id, final String text, final String orthography) {
        this.id = id;
        this.text = text;
        this.orthography = orthography;
    }

    public static CorpusTextSmallDto from(final String id) {
        return new CorpusTextSmallDto(id, "", "");
    }

    public static CorpusTextSmallDto from(final CorpusText corpusText) {
        if (corpusText == null) {
            return new CorpusTextSmallDto("", "", "");
        }
        return new CorpusTextSmallDto(
                corpusText.getId(),
                corpusText.getText(),
                corpusText.getOrthography());
    }

}
