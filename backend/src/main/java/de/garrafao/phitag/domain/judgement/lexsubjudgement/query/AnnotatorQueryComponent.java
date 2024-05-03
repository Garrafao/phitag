package de.garrafao.phitag.domain.judgement.lexsubjudgement.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class AnnotatorQueryComponent implements QueryComponent {

    private final String annotator;

    public AnnotatorQueryComponent(final String annotator) {
        this.annotator = annotator;
    }
    
}
