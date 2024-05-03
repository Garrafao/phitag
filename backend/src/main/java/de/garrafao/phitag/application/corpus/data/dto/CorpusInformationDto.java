package de.garrafao.phitag.application.corpus.data.dto;

import de.garrafao.phitag.domain.corpusinformation.CorpusInformation;
import lombok.Getter;

@Getter
public class CorpusInformationDto {

    private final String id;

    private final String title;
    private final String author;

    private final Integer date;
    private final String language;
    private final String resource;

    private final String corpusnameFull;
    private final String corpusnameShort;

    public CorpusInformationDto(
            final String id, final String title, final String author,
            final int date, final String language, final String resource,
            final String corpusnameFull, final String corpusnameShort) {
        this.id = id;

        this.title = title;
        this.author = author;
        this.date = date;
        this.language = language;
        this.resource = resource;

        this.corpusnameFull = corpusnameFull;
        this.corpusnameShort = corpusnameShort;
    }

    public static CorpusInformationDto from(CorpusInformation corpusInformation) {
        return new CorpusInformationDto(
                corpusInformation.getId(),
                corpusInformation.getTitle(),
                corpusInformation.getAuthor(),
                corpusInformation.getDate(),
                corpusInformation.getLanguage(),
                corpusInformation.getResource(),
                corpusInformation.getCorpusnamefull(),
                corpusInformation.getCorpusnameshort());
    }
}
