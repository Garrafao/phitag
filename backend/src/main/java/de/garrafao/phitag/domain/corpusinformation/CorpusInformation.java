package de.garrafao.phitag.domain.corpusinformation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Entity
@Table(name = "phitagcorpusinformation")
@Getter
public class CorpusInformation {

    @Id
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "date")
    private Integer date;

    @Column(name = "language")
    private String language;

    @Column(name = "resource")
    private String resource;

    @Column(name = "corpusnamefull")
    private String corpusnamefull;

    @Column(name = "corpusnameshort")
    private String corpusnameshort;

    public CorpusInformation() {
    }

    public CorpusInformation(
            final String id, final String title, final String author,
            final int date, final String language, final String resource,
            final String corpusnameFull, final String corpusnameShort) {
        Validate.notBlank(id, "id must not be blank");
        this.id = id;

        this.title = title;
        this.author = author;
        this.date = date;
        this.language = language;
        this.resource = resource;

        this.corpusnamefull = corpusnameFull;
        this.corpusnameshort = corpusnameShort;
    }

    @Override
    public String toString() {
        return "CorpusInformation [id=" + id + ", title=" + title + ", author=" + author + ", date=" + date
                + ", language="
                + language + ", resource=" + resource + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + date;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((language == null) ? 0 : language.hashCode());
        result = prime * result + ((resource == null) ? 0 : resource.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CorpusInformation other = (CorpusInformation) obj;
        return id.equals(other.id);
    }
}
